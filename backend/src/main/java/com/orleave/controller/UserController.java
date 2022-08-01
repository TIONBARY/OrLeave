package com.orleave.controller;

import java.util.NoSuchElementException;

import javax.mail.SendFailedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orleave.auth.SsafyUserDetails;
import com.orleave.dto.request.EmailCheckCodeRequestDto;
import com.orleave.dto.request.EmailConfirmRequestDto;
import com.orleave.dto.request.LoginRequestDto;
import com.orleave.dto.request.SignupRequestDto;
import com.orleave.dto.response.BaseResponseDto;
import com.orleave.dto.response.LoginResponseDto;
import com.orleave.dto.response.UserResponseDto;
import com.orleave.entity.User;
import com.orleave.exception.EmailTimeoutException;
import com.orleave.service.EmailService;
import com.orleave.service.UserService;
import com.orleave.util.JwtTokenUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService mailService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공", response = LoginResponseDto.class),
        @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseDto.class),
        @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseDto.class),
        @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseDto.class)
    })
	public ResponseEntity<LoginResponseDto> login(@RequestBody @ApiParam(value="로그인 정보", required = true) LoginRequestDto loginInfo) {
		String email = loginInfo.getEmail();
		String password = loginInfo.getPassword();
		User user;
		try {
			user = userService.getUserByEmail(email);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body(LoginResponseDto.of(404, "Invalid Email", null));
		}
		String userNo = Integer.toString(user.getNo());
		String userType = user.getUserType();
		int imageNo = user.getImageNo();
		String nickname=user.getNickname();
		// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
		if(passwordEncoder.matches(password, user.getPassword())) {
			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
			return ResponseEntity.ok(LoginResponseDto.of(200, "Success", JwtTokenUtil.getToken(userNo,userType,imageNo,nickname)));
		}
		// 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
		return ResponseEntity.status(401).body(LoginResponseDto.of(401, "Invalid Password", null));
	}
	
	@PostMapping("")
	@ApiOperation(value = "회원 가입", notes = "사용자의 개인 정보를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "부적절한 요청"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) SignupRequestDto signupInfo) {
		
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		try {
			User user = userService.createUser(signupInfo);
			return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Invalid Input"));
		}
	}
	
	@GetMapping("/info")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 403, message = "액세스 토큰 없음"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserResponseDto> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String email = userDetails.getUsername();
		User user = userService.getUserByEmail(email);
		
		return ResponseEntity.status(200).body(UserResponseDto.of(user));
	}
	
	@PostMapping("/email")
    @ApiOperation(value = "회원 가입시 이메일 인증", notes = "기존 사용하고 있는 이메일을 통해 인증")
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "부적절한 요청"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseDto> emailConfirm(
            @RequestBody @ApiParam(value="이메일정보", required = true) EmailConfirmRequestDto emailConfirmRequestDto) throws Exception {
		try {
			String confirm = mailService.sendSimpleMessage(emailConfirmRequestDto.getEmail());
			return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Invalid Email"));
		}
    }
	
	@PostMapping("/code")
    @ApiOperation(value = "이메일 인증코드 확인", notes = "사용 중인 이메일로 보낸 인증 코드 확인")
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "부적절한 요청"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseDto> emailCheckCode(
            @RequestBody @ApiParam(value="이메일과 인증코드", required = true) EmailCheckCodeRequestDto emailCheckCodeRequestDto) throws Exception {
		try {
			String email = emailCheckCodeRequestDto.getEmail();
			String code = emailCheckCodeRequestDto.getCode();
			boolean success = mailService.checkCode(email, code);
			if (success) return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
			else return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Wrong Code"));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Invalid Email"));
		} catch (EmailTimeoutException e) {
			return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Timeout"));
		}
    }
	
	@GetMapping("/email")
    @ApiOperation(value = "이메일 중복여부 확인", notes = "해당 이메일이 현재 사용 중인 이메일인지 확인")
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "중복되는 이메일"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseDto> emailCheck(
            @RequestParam @ApiParam(value="이메일", required = true) String email) throws Exception {
		try {
			User user = userService.getUserByEmail(email);
			return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Duplicate Email"));
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
		}
    }
	
	@GetMapping("/nickname")
    @ApiOperation(value = "닉네임 중복여부 확인", notes = "해당 닉네임이 현재 사용 중인 닉네임인지 확인")
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "중복되는 닉네임"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseDto> nicknameCheck(
            @RequestParam @ApiParam(value="닉네임", required = true) String nickname) throws Exception {
		try {
			User user = userService.getUserByNickname(nickname);
			return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Duplicate Nickname"));
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
		}
    }
}

package com.orleave.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orleave.auth.SsafyUserDetails;
import com.orleave.dto.request.EmailCheckCodeRequestDto;
import com.orleave.dto.request.EmailConfirmRequestDto;
import com.orleave.dto.request.LoginRequestDto;
import com.orleave.dto.request.PasswordRequestDto;
import com.orleave.dto.request.ProfileModifyRequestDto;
import com.orleave.dto.request.SignupRequestDto;
import com.orleave.dto.response.BaseResponseDto;
import com.orleave.dto.response.LoginResponseDto;
import com.orleave.dto.response.ProfileResponseDto;
import com.orleave.entity.User;
import com.orleave.exception.UserNotFoundException;
import com.orleave.service.EmailService;
import com.orleave.service.OauthService;
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
	OauthService oauthService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "아이디와 패스워드를 통해 로그인 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공", response = LoginResponseDto.class),
        @ApiResponse(code = 400, message = "비밀번호 틀림"),
        @ApiResponse(code = 403, message = "로그인 횟수 초과"),
        @ApiResponse(code = 404, message = "해당 사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<LoginResponseDto> login(@RequestBody @ApiParam(value="로그인 정보", required = true) LoginRequestDto loginInfo) throws Exception {
		String email = loginInfo.getEmail();
		String password = loginInfo.getPassword();
		User user = userService.getUserByEmail(email);
		userService.logincheck(user.getNo());
		// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
		if (passwordEncoder.matches(password, user.getPassword())) {
			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
			return ResponseEntity.ok(LoginResponseDto.of(200, "Success", JwtTokenUtil.getToken(user)));
		}
		userService.loginfailed(user.getNo());
		return ResponseEntity.status(401).body(LoginResponseDto.of(401, "Invalid Password", null));
	}
	
	@GetMapping("/login-kakao")
    public ResponseEntity<LoginResponseDto> kakaoCallback(@RequestParam String code) throws IOException {
       String access_Token = oauthService.getKaKaoAccessToken(code);
       String email = oauthService.createKakaoUser(access_Token);
       try {
    	   User user = userService.getUserByEmail(email);
    	   return ResponseEntity.status(200).body(LoginResponseDto.of(200, "Success", JwtTokenUtil.getToken(user)));
       } catch (UsernameNotFoundException e) {
    	   System.out.println("Failed "+email);
    	   return ResponseEntity.status(400).body(LoginResponseDto.of(400, "Signup Required", email));
       }
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
			@RequestBody @ApiParam(value="회원가입 정보", required = true) SignupRequestDto signupInfo) throws Exception {
		userService.createUser(signupInfo);
		return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
	}
	
	@GetMapping("/profile")
	@ApiOperation(value = "회원 프로필 정보 조회", notes = "로그인한 회원 본인의 프로필 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> getProfile(@ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String email = userDetails.getUsername();
		User user = userService.getUserByEmail(email);
		return ResponseEntity.status(200).body(ProfileResponseDto.of(200, "Success", user));
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
		mailService.sendSimpleMessage(emailConfirmRequestDto.getEmail());
		return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
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
		String email = emailCheckCodeRequestDto.getEmail();
		String code = emailCheckCodeRequestDto.getCode();
		if (mailService.checkCode(email, code)) {
			return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
		} else {
			return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Wrong Code"));
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
			userService.getUserByEmail(email);
			return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Duplicate Email"));
		} catch (UsernameNotFoundException e) {
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
			userService.getUserByNickname(nickname);
			return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Duplicate Nickname"));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
		}
    }
	
	@PutMapping("/profile")
    @ApiOperation(value = "프로필 수정", notes = "현재 로그인한 사용자의 프로필 수정")
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "프로필 수정 실패"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseDto> modifyProfile(
            @RequestBody @ApiParam(value="프로필", required = true) ProfileModifyRequestDto profileModifyRequestDto,
            @ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String email = userDetails.getUsername();
		User user = userService.getUserByEmail(email);
		userService.modifyProfile(user.getNo(), profileModifyRequestDto);
		return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Modified"));
    }
	
	@PostMapping("/password")
	@ApiOperation(value = "비밀번호 검사", notes = "기존 비밀번호와 일치하는지 검사")
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "비밀번호 불일치"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseDto> passwordcheck(
            @RequestBody @ApiParam(value="비밀번호", required = true) PasswordRequestDto passwordRequestDto,
            @ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String email = userDetails.getUsername();
		User user = userService.getUserByEmail(email);
		userService.passwordcheck(user.getNo(), passwordRequestDto.getPassword());
		return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
    }
	
	@PutMapping("/password")
	@ApiOperation(value = "비밀번호 변경", notes = "새로운 비밀번호로 변경")
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "비밀번호 변경 실패"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseDto> modifypassword(
            @RequestBody @ApiParam(value="비밀번호", required = true) PasswordRequestDto passwordRequestDto,
            @ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String email = userDetails.getUsername();
		User user = userService.getUserByEmail(email);
		userService.modifypassword(user.getNo(), passwordRequestDto.getPassword());
		return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Modified"));
    }
	
	@DeleteMapping("")
	@ApiOperation(value = "회원 탈퇴", notes = "회원 정보 삭제")
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "회원 탈퇴 실패"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseDto> withdrawal(
            @ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String email = userDetails.getUsername();
		User user = userService.getUserByEmail(email);
		userService.deleteUser(user);
		return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
    }
}

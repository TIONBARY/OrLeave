package com.orleave.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orleave.dto.UserListDto;
import com.orleave.dto.request.LoginRequestDto;
import com.orleave.dto.response.BaseResponseDto;
import com.orleave.dto.response.LoginResponseDto;
import com.orleave.dto.response.UserListResponseDto;
import com.orleave.entity.User;
import com.orleave.service.ManagerService;
import com.orleave.service.UserService;
import com.orleave.util.JwtTokenUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "관리자 API", tags = {"Manager"})
@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ManagerService managerService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공", response = LoginResponseDto.class),
        @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseDto.class),
        @ApiResponse(code = 403, message = "로그인 횟수 초과", response = BaseResponseDto.class),
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
		
		if(!userService.logincheck(user.getNo())) {
			return ResponseEntity.status(403).body(LoginResponseDto.of(403, "forbidden", null));
		}
		String userType = user.getUserType();
		int imageNo = user.getImageNo();
		String nickname=user.getNickname();
		String gender=user.getGender();
		// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
		if(passwordEncoder.matches(password, user.getPassword()) && userType.equals("manager")) {
			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
			return ResponseEntity.ok(LoginResponseDto.of(200, "Success", JwtTokenUtil.getToken(userNo,userType,imageNo,nickname,gender)));
		}
		userService.loginfailed(user.getNo());
		return ResponseEntity.status(401).body(LoginResponseDto.of(401, "Invalid Password", null));
	}

	
	@GetMapping("/users")
	@ApiOperation(value = "유저 목록 전체 조회", notes = "유저 목록을 페이지 정보에 따라 전체 조회한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserListResponseDto> getAllUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
		Page<UserListDto> userList = managerService.getUsers(PageRequest.of(page, size, Sort.by("no").descending()));
		return ResponseEntity.status(200).body(UserListResponseDto.of(200, "Success", userList));
	}
	
}


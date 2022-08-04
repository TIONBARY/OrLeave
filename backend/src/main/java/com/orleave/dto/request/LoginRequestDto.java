package com.orleave.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API ([POST] /api/v1/auth/login) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserLoginPostRequest")
public class LoginRequestDto {
	@ApiModelProperty(name="email", example="ssafy_web@ssafy.com")
	String email;
	@ApiModelProperty(name="password", example="your_password")
	String password;
}

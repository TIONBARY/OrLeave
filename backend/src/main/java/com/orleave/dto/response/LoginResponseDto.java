package com.orleave.dto.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 로그인 API ([POST] /api/v1/auth) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserLoginPostResponse")
public class LoginResponseDto extends BaseResponseDto{
	
	@ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
	String authorization;
	
	public static LoginResponseDto of(Integer statusCode, String message, String authorization) {
		LoginResponseDto res = new LoginResponseDto(statusCode, message, authorization);
		return res;
	}

	public LoginResponseDto(Integer statusCode, String message, String authorization) {
		super(statusCode, message);
		this.authorization = authorization;
	}
}

package com.orleave.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="User Email")
	String email;
	
	public static UserResponseDto of(int statusCode, String message, String email) {
		UserResponseDto res = new UserResponseDto(statusCode, email, email);
		return res;
	}

	public UserResponseDto(Integer statusCode, String message, String email) {
		super(statusCode, message);
		this.email = email;
	}
}

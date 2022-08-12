package com.orleave.dto.response;



import com.orleave.entity.User;

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
		UserResponseDto res = new UserResponseDto();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setEmail(email);
		return res;
	}
}

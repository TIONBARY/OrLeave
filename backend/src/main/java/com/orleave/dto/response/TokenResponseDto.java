package com.orleave.dto.response;

import com.orleave.dto.ProfileDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("TokenResponse")
public class TokenResponseDto extends BaseResponseDto {
	@ApiModelProperty(name="Token for Session")
	String token;
	
	public static TokenResponseDto of(Integer statusCode, String message, String token) {
		TokenResponseDto dto = new TokenResponseDto();
		dto.setStatusCode(statusCode);
		dto.setMessage(message);
		dto.setToken(token);
		return dto;
	}
}

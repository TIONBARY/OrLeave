package com.orleave.dto.response;

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
		TokenResponseDto res = new TokenResponseDto(statusCode, token, token);
		return res;
	}

	public TokenResponseDto(Integer statusCode, String message, String token) {
		super(statusCode, message);
		this.token = token;
	}
}

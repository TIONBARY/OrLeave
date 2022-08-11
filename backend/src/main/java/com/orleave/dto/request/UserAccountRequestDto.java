package com.orleave.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PasswordRequest")
public class UserAccountRequestDto {
	@ApiModelProperty(name="email", example="ssafy_web@ssafy.com")
	String email;
	@ApiModelProperty(name="password", example="your_password")
	String password;
}
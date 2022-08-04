package com.orleave.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EmailConfirmRequest")
public class EmailCheckCodeRequestDto {
	@ApiModelProperty(name="email", example="ssafy_web@ssafy.com")
	String email;
	@ApiModelProperty(name="code", example="1q2w3e4r")
	String code;
}

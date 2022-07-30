package com.orleave.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EmailConfirmRequest")
public class EmailCheckCodeRequestDto {
	@ApiModelProperty(name="인증할 Email", example="ssafy_web@ssafy.com")
	String email;
	@ApiModelProperty(name="인증 코드", example="1q2w3e4r")
	String code;
}

package com.orleave.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EmailConfirmRequest")
public class EmailConfirmRequestDto {
	@ApiModelProperty(name="email", example="ssafy_web@ssafy.com")
	String email;
}

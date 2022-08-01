package com.orleave.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PasswordCheckRequest")
public class PasswordCheckRequestDto {

	@ApiModelProperty(name="유저 Password", example="your_password")
	String password;
}

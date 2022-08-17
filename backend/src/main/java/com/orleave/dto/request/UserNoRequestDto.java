package com.orleave.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserNoRequest")
public class UserNoRequestDto {
	@ApiModelProperty(name="userNo")
	int no;
}

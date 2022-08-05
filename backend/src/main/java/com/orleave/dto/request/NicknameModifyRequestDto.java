package com.orleave.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("NickNameModifyRequest")
public class NicknameModifyRequestDto {
	@ApiModelProperty(name="userno")
	int no;
	@ApiModelProperty(name="nickname")
	String nickname;
}

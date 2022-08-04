package com.orleave.dto.request;

import java.util.ArrayList;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProfileModifyRequest")
public class ProfileModifyRequestDto {
	@ApiModelProperty(name="nickname", example="nickname")
	String nickname;
	@ApiModelProperty(name="mbti", example="ISFJ")
	String mbti;
	@ApiModelProperty(name="religion", example="1")
	int religion;
	@ApiModelProperty(name="smoke", example="0")
	int smoke;
	@ApiModelProperty(name="drink", example="0")
	int drink;
	@ApiModelProperty(name="imageNo", example="10")
	int imageNo;
	@ApiModelProperty(name="interests", example="[1, 2, 3]")
	ArrayList<Integer> interests;
	@ApiModelProperty(name="사용자 성격", example="[1, 3, 5]")
	ArrayList<Integer> personalities;
}

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
	@ApiModelProperty(name="사용자 닉네임", example="nickname")
	String nickname;
	@ApiModelProperty(name="사용자 MBTI", example="ISFJ")
	String mbti;
	@ApiModelProperty(name="사용자 종교", example="1")
	int religion;
	@ApiModelProperty(name="사용자 흡연 여부", example="0")
	int smoke;
	@ApiModelProperty(name="사용자 음주 여부", example="0")
	int drink;
	@ApiModelProperty(name="사용자 프로필 이미지 번호", example="10")
	int imageNo;
	@ApiModelProperty(name="사용자 관심사", example="[1, 2, 3]")
	ArrayList<Integer> interests;
	@ApiModelProperty(name="사용자 성격", example="[1, 3, 5]")
	ArrayList<Integer> personalities;
}

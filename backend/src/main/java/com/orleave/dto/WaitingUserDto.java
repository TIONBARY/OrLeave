package com.orleave.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("WaitingUserDto")
public class WaitingUserDto {
	@ApiModelProperty(name="사용자 번호")
	int no;
	@ApiModelProperty(name="사용자 나이")
	int age;
	@ApiModelProperty(name="사용자 현재 위치 위도")
	double lat;
	@ApiModelProperty(name="사용자 현재 위치 경도")
	double lng;
	@ApiModelProperty(name="사용자 닉네임")
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
}

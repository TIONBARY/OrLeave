package com.orleave.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel("ProfileDto")
public class ProfileDto {
	
	@ApiModelProperty(name="이메일")
	String email;
	@ApiModelProperty(name="성별")
	String gender;
	@ApiModelProperty(name="생일")
	LocalDate birthDay;
	@ApiModelProperty(name="별명")
	String nickname;
	@ApiModelProperty(name="MBTI")
	String mbti;
	@ApiModelProperty(name="종교")
	int religion;
	@ApiModelProperty(name="음주량")
	int drink;
	@ApiModelProperty(name="흡연여부")
	int smoke;
	@ApiModelProperty(name="프로필 이미지")
	int imageNo;
	@ApiModelProperty(name="취미")
	ArrayList<Integer> interests;
	@ApiModelProperty(name="성격")
	ArrayList<Integer> personalities;
}

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
	@ApiModelProperty(name="User Email")
	String email;
	String gender;
	LocalDate birthDay;
	String nickname;
	String mbti;
	int religion;
	int drink;
	int smoke;
	int imageNo;
	ArrayList<Integer> interests;
	ArrayList<Integer> personalities;
}

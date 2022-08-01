package com.orleave.dto.request;

import java.time.LocalDate;
import java.util.ArrayList;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProfileModifyRequest")
public class ProfileModifyRequestDto {
	String nickname;
	String mbti;
	int religion;
	int smoke;
	int drink;
	int imageNo;
	ArrayList<Integer> interests;
	ArrayList<Integer> personalities;
}

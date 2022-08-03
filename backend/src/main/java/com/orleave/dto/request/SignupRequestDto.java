package com.orleave.dto.request;

import java.time.LocalDate;
import java.util.ArrayList;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserRegisterPostRequest")
public class SignupRequestDto {
	@ApiModelProperty(name="유저 Email", example="ssafy_web@ssafy.com")
	String email;
	@ApiModelProperty(name="유저 Password", example="your_password")
	String password;
	@ApiModelProperty(name="사용자 성별", example="M")
	String gender;
	@ApiModelProperty(name="사용자 생년월일", example="2000-01-01")
	LocalDate birthDay;
	@ApiModelProperty(name="사용자 닉네임", example="nickname")
	String nickname;
	@ApiModelProperty(name="사용자 MBTI", example="ISFJ")
	String mbti;
	@ApiModelProperty(name="사용자 종교", example="0")
	int religion;
	@ApiModelProperty(name="사용자 흡연 여부", example="0")
	int smoke;
	@ApiModelProperty(name="사용자 음주 여부", example="0")
	int drink;
	@ApiModelProperty(name="사용자 프로필 이미지 번호", example="3")
	int imageNo;
	@ApiModelProperty(name="사용자 관심사", example="[1, 2, 3]")
	ArrayList<Integer> interests;
	@ApiModelProperty(name="사용자 성격", example="[1, 3, 5]")
	ArrayList<Integer> personalities;
}

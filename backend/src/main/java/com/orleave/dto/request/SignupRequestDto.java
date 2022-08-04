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
	@ApiModelProperty(name="email", example="ssafy_web@ssafy.com")
	String email;
	@ApiModelProperty(name="password", example="your_password")
	String password;
	@ApiModelProperty(name="gender", example="M")
	String gender;
	@ApiModelProperty(name="birthDay", example="2000-01-01")
	LocalDate birthDay;
	@ApiModelProperty(name="nickname", example="nickname")
	String nickname;
	@ApiModelProperty(name="mbti", example="ISFJ")
	String mbti;
	@ApiModelProperty(name="religion", example="0")
	int religion;
	@ApiModelProperty(name="smoke", example="0")
	int smoke;
	@ApiModelProperty(name="drink", example="0")
	int drink;
	@ApiModelProperty(name="imageNo", example="3")
	int imageNo;
	@ApiModelProperty(name="interests", example="[1, 2, 3]")
	ArrayList<Integer> interests;
	@ApiModelProperty(name="personalities", example="[1, 3, 5]")
	ArrayList<Integer> personalities;
}

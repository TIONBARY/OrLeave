package com.orleave.dto.response;



import java.time.LocalDate;
import java.util.ArrayList;

import com.orleave.entity.User;
import com.orleave.entity.UserInterest;
import com.orleave.entity.UserPersonality;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@Builder
@ApiModel("ProfileResponse")
public class ProfileResponseDto{
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
	
	
	public static ProfileResponseDto of(User user) {
		ProfileResponseDto res = ProfileResponseDto.builder()
				.email(user.getEmail())
				.gender(user.getGender())
				.birthDay(user.getBirthDay())
				.nickname(user.getNickname())
				.mbti(user.getMbti())
				.religion(user.getReligion())
				.smoke(user.getSmoke())
				.drink(user.getDrink())
				.imageNo(user.getImageNo())
				.build();
		res.setInterests(new ArrayList<Integer>());
		for (UserInterest interest : user.getInterests()) {
			res.interests.add(interest.getInterest());
		}
		res.setPersonalities(new ArrayList<Integer>());
		for (UserPersonality personality : user.getPersonalities()) {
			res.personalities.add(personality.getPersonality());
		}
		return res;
	}
}

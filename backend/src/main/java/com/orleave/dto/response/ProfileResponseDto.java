package com.orleave.dto.response;

import java.util.ArrayList;

import com.orleave.dto.ProfileDto;
import com.orleave.entity.User;
import com.orleave.entity.UserInterest;
import com.orleave.entity.UserPersonality;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 프로필 정보 조회 API ([GET] /api/v1/users/profile) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ProfileResponse")
public class ProfileResponseDto extends BaseResponseDto {
	@ApiModelProperty(name="User Profile")
	ProfileDto profile;
	
	public static ProfileResponseDto of(Integer statusCode, String message, User user) {
		ProfileDto profileDto = ProfileDto.builder()
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
		profileDto.setInterests(new ArrayList<Integer>());
		for (UserInterest interest : user.getInterests()) {
			profileDto.getInterests().add(interest.getInterest());
		}
		profileDto.setPersonalities(new ArrayList<Integer>());
		for (UserPersonality personality : user.getPersonalities()) {
			profileDto.getPersonalities().add(personality.getPersonality());
		}
		ProfileResponseDto profileResponseDto = new ProfileResponseDto();
		profileResponseDto.setStatusCode(statusCode);
		profileResponseDto.setMessage(message);
		profileResponseDto.setProfile(profileDto);
		return profileResponseDto;
	}
}

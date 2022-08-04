package com.orleave.dto.response;

import com.orleave.entity.MeetingSetting;


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
@ApiModel("MeetingSettingResponse")
public class MeetingSettingResponseDto{
	int religion;
	int smoke;
	int drink_min;
	int drink_max;
	int distance;
	int age_min;
	int age_max;
	
	
	public static MeetingSettingResponseDto of(MeetingSetting meetingsetting) {
		MeetingSettingResponseDto res = MeetingSettingResponseDto.builder()
				.religion(meetingsetting.getReligion())
				.smoke(meetingsetting.getSmoke())
				.drink_max(meetingsetting.getDrinkMax())
				.drink_min(meetingsetting.getDrinkMin())
				.distance(meetingsetting.getDistance())
				.age_max(meetingsetting.getAgeMax())
				.age_min(meetingsetting.getAgeMin())
				.build();
		return res;
	}
}

package com.orleave.dto.response;

import com.orleave.dto.MeetingSettingDto;
import com.orleave.entity.MeetingSetting;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MeetingSettingResponse")
public class MeetingSettingResponseDto extends BaseResponseDto{
	MeetingSettingDto meetingsetting;
	
	
	public static MeetingSettingResponseDto of(Integer statusCode, String message,MeetingSetting meetingsetting) {
		MeetingSettingDto res = MeetingSettingDto.builder()
				.religion(meetingsetting.getReligion())
				.smoke(meetingsetting.getSmoke())
				.drink_max(meetingsetting.getDrinkMax())
				.drink_min(meetingsetting.getDrinkMin())
				.distance(meetingsetting.getDistance())
				.age_max(meetingsetting.getAgeMax())
				.age_min(meetingsetting.getAgeMin())
				.build();
		MeetingSettingResponseDto Dto=new MeetingSettingResponseDto();
		Dto.setStatusCode(statusCode);
		Dto.setMessage(message);
		Dto.setMeetingsetting(res);
		return Dto;
	}
}

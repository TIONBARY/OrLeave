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
@ApiModel("MeetingSettingResponse")
public class MeetingSettingResponseDto extends BaseResponseDto{
	
	MeetingSettingDto meetingSetting;
	
	public static MeetingSettingResponseDto of(Integer statusCode, String message, MeetingSettingDto meetingSetting) {
		MeetingSettingResponseDto res=new MeetingSettingResponseDto(statusCode, message, meetingSetting);
		return res;
	}

	public MeetingSettingResponseDto(Integer statusCode, String message, MeetingSettingDto meetingSetting) {
		super(statusCode, message);
		this.meetingSetting = meetingSetting;
	}
}

package com.orleave.dto.response;

import java.util.List;

import com.orleave.dto.MeetingLogListDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("MeetingLogResponse")
public class MeetingLogResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="Meeting Log List")
	List<MeetingLogListDto> meetingLogList;
	
	public static MeetingLogResponseDto of(Integer statusCode, String message, List<MeetingLogListDto> meetingLogList) {
		MeetingLogResponseDto res = new MeetingLogResponseDto(statusCode, message, meetingLogList);
		return res;
	}

	public MeetingLogResponseDto(Integer statusCode, String message, List<MeetingLogListDto> meetingLogList) {
		super(statusCode, message);
		this.meetingLogList = meetingLogList;
	}
	
}

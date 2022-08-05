package com.orleave.service;

import java.util.List;

import com.orleave.dto.MeetingLogListDto;
import com.orleave.dto.request.MeetingSettingRequestDto;
import com.orleave.entity.MeetingSetting;

public interface MeetingService {

	MeetingSetting getSettingByNo(int no);
	boolean modifyMeetingSetting(int no, MeetingSettingRequestDto meetingSettingRequestDto);
	List<MeetingLogListDto> getRecentMeetingLogs(int userNo);
}

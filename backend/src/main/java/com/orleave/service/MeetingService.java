package com.orleave.service;

import java.util.List;

import com.orleave.dto.MeetingLogListDto;
import com.orleave.dto.request.MeetingSettingRequestDto;
import com.orleave.dto.request.ReportRequestDto;
import com.orleave.entity.MeetingSetting;
import com.orleave.entity.User;

public interface MeetingService {

	MeetingSetting getSettingByNo(int no)throws Exception;
	void modifyMeetingSetting(int no, MeetingSettingRequestDto meetingSettingRequestDto)throws Exception;
	List<MeetingLogListDto> getRecentMeetingLogs(int userNo)throws Exception;
	void reportUser(User user, ReportRequestDto reportRequestDto)throws Exception;
}

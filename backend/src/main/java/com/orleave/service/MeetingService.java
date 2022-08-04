package com.orleave.service;

import com.orleave.dto.request.MeetingSettingRequestDto;
import com.orleave.entity.MeetingSetting;

public interface MeetingService {

	MeetingSetting getSettingByNo(int no);

	boolean modifyMeetingSetting(int no, MeetingSettingRequestDto meetingSettingRequestDto);

}

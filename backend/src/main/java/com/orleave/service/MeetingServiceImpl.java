package com.orleave.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orleave.dto.MeetingLogListDto;
import com.orleave.dto.request.MeetingSettingRequestDto;
import com.orleave.entity.MeetingLog;
import com.orleave.entity.MeetingSetting;
import com.orleave.repository.MeetingLogRepository;
import com.orleave.repository.MeetingSettingRepository;
import com.orleave.repository.UserRepository;


@Service("MeetingService")
public class MeetingServiceImpl implements MeetingService{

	
	@Autowired
	MeetingSettingRepository meetingSettingRepository;
	
	@Autowired
	MeetingLogRepository meetingLogRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public MeetingSetting getSettingByNo(int no) {
		
		MeetingSetting meetingsetting = meetingSettingRepository.findById(no).get();
		return meetingsetting;
	}

	@Override
	public boolean modifyMeetingSetting(int no, MeetingSettingRequestDto dto) {
		MeetingSetting meetingsetting = meetingSettingRepository.findById(no).get();
			meetingsetting.setReligion(dto.getReligion());
			meetingsetting.setSmoke(dto.getSmoke());
			meetingsetting.setDrinkMax(dto.getDrink_max());
			meetingsetting.setDrinkMin(dto.getDrink_min());
			meetingsetting.setDistance(dto.getDistance());
			meetingsetting.setAgeMax(dto.getAge_max());
			meetingsetting.setAgeMin(dto.getAge_min());
			meetingSettingRepository.save(meetingsetting);
		return true;
	}
	
	@Override
	public List<MeetingLogListDto> getRecentMeetingLogs(int userNo) {
		List<MeetingLog> list = meetingLogRepository.findByUser1NoAndCreatedTimeBetween(userNo, LocalDateTime.now().minusDays(7), LocalDateTime.now());
		List<MeetingLogListDto> logs = new ArrayList<>();
		for (MeetingLog log : list) {
			int user2No = log.getUser2();
			MeetingLogListDto dto = MeetingLogListDto.builder()
					.no(log.getNo())
					.userNo(user2No)
					.nickname(userRepository.findById(user2No).get().getNickname())
					.createdTime(log.getCreatedTime())
					.build();
			logs.add(dto);
		}
		return logs;
	}
}

package com.orleave.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orleave.dto.MeetingLogListDto;
import com.orleave.dto.MeetingSettingDto;
import com.orleave.dto.request.MeetingSettingRequestDto;
import com.orleave.dto.request.ReportRequestDto;
import com.orleave.entity.MeetingLog;
import com.orleave.entity.MeetingSetting;
import com.orleave.entity.Report;
import com.orleave.entity.User;
import com.orleave.exception.UserNotFoundException;
import com.orleave.repository.MeetingLogRepository;
import com.orleave.repository.MeetingSettingRepository;
import com.orleave.repository.ReportRepository;
import com.orleave.repository.UserRepository;

import io.swagger.annotations.ApiModelProperty;


@Service("MeetingService")
public class MeetingServiceImpl implements MeetingService{

	@Autowired
	MeetingSettingRepository meetingSettingRepository;
	
	@Autowired
	MeetingLogRepository meetingLogRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ReportRepository reportRepository;
	
	@Override
	public MeetingSettingDto getSettingByNo(int no) throws Exception{
		Optional<MeetingSetting> meetingSettingTemp = meetingSettingRepository.findById(no);
		if(!meetingSettingTemp.isPresent()) throw new UserNotFoundException();
		MeetingSetting meetingsetting=meetingSettingTemp.get();
		
		MeetingSettingDto dto = MeetingSettingDto.builder()
				.religion(meetingsetting.getReligion())
				.smoke(meetingsetting.getSmoke())
				.drinkMin(meetingsetting.getDrinkMin())
				.drinkMax(meetingsetting.getDrinkMax())
				.distance(meetingsetting.getDistance())
				.ageMin(meetingsetting.getAgeMin())
				.ageMax(meetingsetting.getAgeMax())
				.build();
		return dto;
	}

	@Override
	public void modifyMeetingSetting(int no, MeetingSettingRequestDto dto) throws Exception{
		Optional<MeetingSetting> meetingsettingtemp = meetingSettingRepository.findById(no);
		if(!meetingsettingtemp.isPresent()) throw new UserNotFoundException();
		
		MeetingSetting meetingSetting = MeetingSetting.builder()
				.religion(dto.getReligion())
				.smoke(dto.getSmoke())
				.drinkMax(dto.getDrink_max())
				.drinkMin(dto.getDrink_min())
				.distance(dto.getDistance())
				.ageMax(dto.getAge_max())
				.ageMin(dto.getAge_min())
				.build();
		meetingSettingRepository.save(meetingSetting);
	}
	
	@Override
	public List<MeetingLogListDto> getRecentMeetingLogs(int userNo) throws Exception{
		Optional<User> user=userRepository.findById(userNo);
		if(!user.isPresent()) throw new UserNotFoundException();
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
	
	@Override
	@Transactional
	public void reportUser(User user, ReportRequestDto dto) throws Exception{
		if(user==null) throw new UserNotFoundException();
		
		Report report = Report.builder()
				.user(user)
				.reported(dto.getReportedNo())
				.category(dto.getCategory())
				.content(dto.getContent())
				.reportTime(LocalDateTime.now())
				.build();
		reportRepository.save(report);
	}
}

package com.orleave.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orleave.dto.MeetingLogListDto;
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
	public MeetingSetting getSettingByNo(int no) throws Exception{
		Optional<MeetingSetting> meetingsettingtemp = meetingSettingRepository.findById(no);
		if(!meetingsettingtemp.isPresent()) throw new UserNotFoundException();
		MeetingSetting meetingsetting=meetingsettingtemp.get();
		return meetingsetting;
	}

	@Override
	public void modifyMeetingSetting(int no, MeetingSettingRequestDto dto) throws Exception{
		Optional<MeetingSetting> meetingsettingtemp = meetingSettingRepository.findById(no);
		if(!meetingsettingtemp.isPresent()) throw new UserNotFoundException();
		MeetingSetting meetingsetting=meetingsettingtemp.get();
		
		meetingsetting.setReligion(dto.getReligion());
		meetingsetting.setSmoke(dto.getSmoke());
		meetingsetting.setDrinkMax(dto.getDrink_max());
		meetingsetting.setDrinkMin(dto.getDrink_min());
		meetingsetting.setDistance(dto.getDistance());
		meetingsetting.setAgeMax(dto.getAge_max());
		meetingsetting.setAgeMin(dto.getAge_min());
		meetingSettingRepository.save(meetingsetting);
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
	public void reportUser(User user, ReportRequestDto reportRequestDto) throws Exception{
		if(user==null) throw new UserNotFoundException();
		Report report = Report.builder()
				.user(user)
				.reported(reportRequestDto.getReportedNo())
				.category(reportRequestDto.getCategory())
				.content(reportRequestDto.getContent())
				.reportTime(LocalDateTime.now())
				.build();
		reportRepository.save(report);
	}
}

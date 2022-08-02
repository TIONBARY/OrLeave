package com.orleave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orleave.dto.request.MeetingSettingRequestDto;
import com.orleave.entity.MeetingSetting;
import com.orleave.repository.MeetingSettingRepository;


@Service("MeetingService")
public class MeetingServiceImpl implements MeetingService{

	
	@Autowired
	MeetingSettingRepository meetingSettingRepository;
	
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
	
	
	

}

package com.orleave.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orleave.dto.WaitingFemaleDto;
import com.orleave.dto.WaitingMaleDto;
import com.orleave.dto.WaitingUserDto;
import com.orleave.entity.User;
import com.orleave.repository.BanRepository;
import com.orleave.repository.MeetingSettingRepository;
import com.orleave.repository.UserRepository;

@Service
public class MatchingServiceImpl implements MatchingService {
	
	@Autowired
	MeetingSettingRepository meetingSettingRepository;
	
	@Autowired
	BanRepository banRepository;
	
	@Autowired
	UserRepository userRepository;
	
	private Map<Integer, WaitingMaleDto> males;
	private Map<Integer, WaitingFemaleDto> females;
	
	@Autowired
	public MatchingServiceImpl() {
		males = new ConcurrentHashMap<>();
		females = new ConcurrentHashMap<>();
	}
	
	@Override
	public void startMatching(int userNo, double lat, double lng) {
		User user = userRepository.findById(userNo).get();
		WaitingUserDto userDto = WaitingUserDto.builder()
				.no(user.getNo())
				.age(LocalDate.now().getYear() - user.getBirthDay().getYear() + 1)
				.nickname(user.getNickname())
				.lat(lat)
				.lng(lng)
				.mbti(user.getMbti())
				.religion(user.getReligion())
				.smoke(user.getSmoke())
				.drink(user.getDrink())
				.imageNo(user.getImageNo())
				.build();
		if (user.getGender() == "M") males.put(userNo, (WaitingMaleDto) userDto);
		else females.put(userNo, (WaitingFemaleDto) userDto);
	}

	@Override
	public WaitingUserDto findMatching(int userNo) {
		return null;
	}

}

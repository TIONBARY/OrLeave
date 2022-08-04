package com.orleave.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orleave.dto.WaitingFemaleDto;
import com.orleave.dto.WaitingMaleDto;
import com.orleave.dto.WaitingUserDto;
import com.orleave.entity.Ban;
import com.orleave.entity.MeetingSetting;
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
	
	private Map<Integer, WaitingUserDto> males;
	private Map<Integer, WaitingUserDto> females;
	
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
		if (user.getGender().equals("M")) males.put(userNo, userDto);
		else females.put(userNo, userDto);
	}

	@Override
	public WaitingUserDto findMatching(int userNo) {
		ArrayList<WaitingUserDto> malesList = new ArrayList<>();
		WaitingUserDto femaleDto = females.get(userNo);
		MeetingSetting femaleMeetingSetting = meetingSettingRepository.findById(userNo).get();
		for (int maleNo : males.keySet()) {
			List<Ban> femaleBan = banRepository.findByUserNo(userNo);
			for (Ban ban : femaleBan) {
				if (ban.getBannedNo() == maleNo) continue;
			}
			List<Ban> maleBan = banRepository.findByUserNo(maleNo);
			for (Ban ban : maleBan) {
				if (ban.getBannedNo() == userNo) continue;
			}
			WaitingUserDto maleDto = males.get(maleNo);
			MeetingSetting maleMeetingSetting = meetingSettingRepository.findById(maleNo).get();
			if (!check(maleMeetingSetting, maleDto, femaleDto)) continue;
			if (!check(femaleMeetingSetting, femaleDto, maleDto)) continue;
			malesList.add(maleDto);
		}
		if (malesList.isEmpty()) return null;
		Random rand = new Random();
		return malesList.get(rand.nextInt(malesList.size()));
	}

	private boolean check(MeetingSetting ms, WaitingUserDto first, WaitingUserDto second) {
		int age = second.getAge();
		if (age < ms.getAgeMin() || age > ms.getAgeMax()) return false;
		double lat1 = first.getLat();
		double lng1 = first.getLng();
		double lat2 = second.getLat();
		double lng2 = second.getLng();
		double distance = distance(lat1, lng1, lat2, lng2);
		if (distance > ms.getDistance()) return false;
		int drink = second.getDrink();
		if (drink < ms.getDrinkMin() || drink > ms.getDrinkMax()) return false;
		int smoke = second.getSmoke();
		if (ms.getSmoke() != 0 && ms.getSmoke()-1 != smoke) return false;
		int religion1 = first.getReligion();
		int religion2 = second.getReligion();
		if (ms.getReligion() == 1 && religion1 != religion2) return false;
		return true;
	}
	
	private static double distance(double lat1, double lng1, double lat2, double lng2) {
		double theta = lng1 - lng2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515 * 1.609344;
		return (dist);
	}
	

	// This function converts decimal degrees to radians
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	// This function converts radians to decimal degrees
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

}

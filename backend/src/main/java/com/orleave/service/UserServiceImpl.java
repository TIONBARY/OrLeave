package com.orleave.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.orleave.dto.ProfileDto;
import com.orleave.dto.request.ProfileModifyRequestDto;
import com.orleave.dto.request.SignupRequestDto;
import com.orleave.entity.Ban;
import com.orleave.entity.Inquiry;
import com.orleave.entity.LoginTrial;
import com.orleave.entity.MeetingSetting;
import com.orleave.entity.User;
import com.orleave.entity.UserInterest;
import com.orleave.entity.UserPersonality;
import com.orleave.exception.LoginProhibitedException;
import com.orleave.exception.UserNotFoundException;
import com.orleave.exception.WrongPasswordException;
import com.orleave.repository.BanRepository;
import com.orleave.repository.InquiryRepository;
import com.orleave.repository.LoginTrialRepository;
import com.orleave.repository.MeetingLogRepository;
import com.orleave.repository.MeetingSettingRepository;
import com.orleave.repository.ReportRepository;
import com.orleave.repository.UserInterestRepository;
import com.orleave.repository.UserPersonalityRepository;
import com.orleave.repository.UserRepository;
import com.orleave.repository.UserRepositorySupport;


/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LoginTrialRepository loginTrialRepository;
	
	@Autowired
	MeetingSettingRepository meetingSettingRepository;
	
	@Autowired
	UserInterestRepository userInterestRepository;
	
	@Autowired
	UserPersonalityRepository userPersonalityRepository;
	
	@Autowired
	BanRepository banRepository;
	
	@Autowired
	ReportRepository reportRepository;
	
	@Autowired
	InquiryRepository inquiryRepository;
	
	@Autowired
	MeetingLogRepository meetingLogRepository;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public void createUser(SignupRequestDto signupInfo) throws Exception {
		User user = User.builder()
				.email(signupInfo.getEmail())
				.password(passwordEncoder.encode(signupInfo.getPassword()))
				.userType("USER")
				.gender(signupInfo.getGender())
				.birthDay(signupInfo.getBirthDay())
				.nickname(signupInfo.getNickname())
				.mbti(signupInfo.getMbti())
				.religion(signupInfo.getReligion())
				.smoke(signupInfo.getSmoke())
				.drink(signupInfo.getDrink())
				.imageNo(signupInfo.getImageNo())
				.build();
		userRepository.save(user);
		LoginTrial loginTrial = LoginTrial.builder()
				.user(user)
				.time(LocalDateTime.of(1970, 1, 1, 0, 0))
				.build();
		loginTrialRepository.save(loginTrial);
		MeetingSetting meetingSetting = MeetingSetting.builder()
				.user(user)
				.religion(0)
				.smoke(0)
				.drinkMin(0)
				.drinkMax(2)
				.distance(4)
				.ageMin(20)
				.ageMax(50)
				.build();
		meetingSettingRepository.save(meetingSetting);
		user.setInterests(new ArrayList<UserInterest>());
		for (int interest : signupInfo.getInterests()) {
			UserInterest userInterest = UserInterest.builder()
					.user(user)
					.interest(interest)
					.build();
			user.addInterest(userInterest);
			userInterestRepository.save(userInterest);
		}
		user.setPersonalities(new ArrayList<UserPersonality>());
		for (int personality : signupInfo.getPersonalities()) {
			UserPersonality userPersonality = UserPersonality.builder()
					.user(user)
					.personality(personality)
					.build();
			user.addPersonality(userPersonality);
			userPersonalityRepository.save(userPersonality);
		}
		user.setInquiries(new ArrayList<Inquiry>());
		user.setBans(new ArrayList<Ban>());
	}
	
	@Override
	public User getUserByNo(int no) throws Exception {
		Optional<User> user = userRepositorySupport.findUserByNo(no);
		if (!user.isPresent()) throw new UserNotFoundException();
		return user.get();
	}
	
	@Override
	public User getUserByEmail(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepositorySupport.findUserByEmail(email);
		if (!user.isPresent()) throw new UsernameNotFoundException("User Not Found");
		return user.get();
	}
	
	@Override
	public User getUserByNickname(String nickname) throws Exception {
		Optional<User> user = userRepositorySupport.findUserByNickname(nickname);
		if (!user.isPresent()) throw new UserNotFoundException();
		return user.get();
	}
	
	@Override
	@Transactional
	public void modifyProfile(int userNo, ProfileModifyRequestDto dto) throws Exception {
		Optional<User> tempUser = userRepositorySupport.findUserByNo(userNo);
		if (!tempUser.isPresent()) throw new UserNotFoundException();
		User user = tempUser.get();
		user.setImageNo(dto.getImageNo());
		user.setMbti(dto.getMbti());
		user.setDrink(dto.getDrink());
		user.setSmoke(dto.getSmoke());
		user.setNickname(dto.getNickname());
		user.setReligion(dto.getReligion());
		for (UserInterest userInterest : user.getInterests()) {
			userInterestRepository.delete(userInterest);
		}
		user.setInterests(new ArrayList<>());
		for (Integer interest : dto.getInterests()) {
			UserInterest userInterest = UserInterest.builder()
					.user(user)
					.interest(interest)
					.build();
			userInterestRepository.save(userInterest);
			user.addInterest(userInterest);
		}
		for (UserPersonality userPersonality : user.getPersonalities()) {
			userPersonalityRepository.delete(userPersonality);
		}
		user.setPersonalities(new ArrayList<>());
		for (Integer personality : dto.getPersonalities()) {
			UserPersonality userPersonality = UserPersonality.builder()
					.user(user)
					.personality(personality)
					.build();
			userPersonalityRepository.save(userPersonality);
			user.addPersonality(userPersonality);
		}
	}

	@Override
	@Transactional
	public void passwordcheck(int userNo, String password) throws Exception {
		Optional<User> tempUser = userRepositorySupport.findUserByNo(userNo);
		if (!tempUser.isPresent()) throw new UserNotFoundException();
		User user = tempUser.get();
		if (!passwordEncoder.matches(password, user.getPassword())){
			throw new WrongPasswordException();
		}
	}

	@Override
	@Transactional
	public void modifypassword(int userNo, String password) throws Exception {
		Optional<User> tempUser = userRepositorySupport.findUserByNo(userNo);
		if (!tempUser.isPresent()) throw new UserNotFoundException();
		User user = tempUser.get();
		if (password != null && !passwordEncoder.matches(password, user.getPassword())) {
			user.setPassword(passwordEncoder.encode(password));
			userRepository.save(user);
			return;
		}
		throw new WrongPasswordException();
	}
	
	@Override
	@Transactional
	public void deleteUser(User user) throws Exception {
		loginTrialRepository.deleteById(user.getNo());
		meetingSettingRepository.deleteById(user.getNo());
		userInterestRepository.deleteByUserNo(user.getNo());
		userPersonalityRepository.deleteByUserNo(user.getNo());
		meetingLogRepository.deleteByUser1No(user.getNo());
		meetingLogRepository.deleteByUser2(user.getNo());
		banRepository.deleteByUserNo(user.getNo());
		banRepository.deleteByBannedNo(user.getNo());
		reportRepository.deleteByUserNo(user.getNo());
		inquiryRepository.deleteByUserNo(user.getNo());
		userRepository.delete(user);
	}

	@Override
	public void loginfailed(int no) throws Exception {
		Optional<LoginTrial> tempLoginTrial = loginTrialRepository.findById(no);
		if (!tempLoginTrial.isPresent()) throw new UserNotFoundException();
		LoginTrial logintrial = tempLoginTrial.get();
		if (logintrial.getCount() == 4) {
			logintrial.setCount(0);
			logintrial.setTime(LocalDateTime.now());
		} else {
			logintrial.setCount(logintrial.getCount()+1);
		}
		loginTrialRepository.save(logintrial);
	}

	@Override
	public void loginSuccess(int no) throws Exception {
		Optional<LoginTrial> tempLoginTrial = loginTrialRepository.findById(no);
		if (!tempLoginTrial.isPresent()) throw new UserNotFoundException();
		LoginTrial logintrial = tempLoginTrial.get();
		logintrial.setCount(0);
		loginTrialRepository.save(logintrial);
	}
	
	@Override
	public void logincheck(int no) throws Exception {
		Optional<LoginTrial> tempLoginTrial = loginTrialRepository.findById(no);
		if (!tempLoginTrial.isPresent()) throw new UserNotFoundException();
		LoginTrial logintrial = tempLoginTrial.get();
		if (Duration.between(logintrial.getTime(), LocalDateTime.now()).getSeconds() <= 60 * 5) {
			throw new LoginProhibitedException();
		}
	}

	@Override
	public ProfileDto getProfileByNo(int userNo) throws Exception {
		Optional<User> userTemp = userRepositorySupport.findUserByNo(userNo);
		if (!userTemp.isPresent()) throw new UserNotFoundException();
		User user = userTemp.get();
		ProfileDto dto = ProfileDto.builder()
				.email(user.getEmail())
				.gender(user.getGender())
				.birthDay(user.getBirthDay())
				.nickname(user.getNickname())
				.mbti(user.getMbti())
				.religion(user.getReligion())
				.smoke(user.getSmoke())
				.drink(user.getDrink())
				.imageNo(user.getImageNo())
				.build();
		dto.setInterests(new ArrayList<Integer>());
		for (UserInterest interest : user.getInterests()) {
			dto.getInterests().add(interest.getInterest());
		}
		dto.setPersonalities(new ArrayList<Integer>());
		for (UserPersonality personality : user.getPersonalities()) {
			dto.getPersonalities().add(personality.getPersonality());
		}
		return dto;
	}
}

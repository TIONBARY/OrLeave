package com.orleave.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orleave.dto.NoticeDetailDto;
import com.orleave.dto.ReportDetailDto;
import com.orleave.dto.UserListDto;
import com.orleave.dto.UserReportListDto;
import com.orleave.dto.request.InquiryAnswerRequestDto;
import com.orleave.dto.request.NicknameModifyRequestDto;
import com.orleave.entity.Inquiry;
import com.orleave.entity.Notice;
import com.orleave.entity.Report;
import com.orleave.entity.User;
import com.orleave.repository.InquiryRepository;
import com.orleave.repository.MeetingLogRepository;
import com.orleave.repository.ReportRepository;
import com.orleave.repository.UserRepository;

@Service("ManagerService")
public class ManagerServiceImpl implements ManagerService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	InquiryRepository inquiryRepository;
	
	@Autowired
	MeetingLogRepository meetingLogRepository;
	
	@Autowired
	ReportRepository reportRepository;
	
	@Override
	public Page<UserListDto> getUsers(Pageable pageable) {
		
		
		
		Page<UserListDto> users = userRepository.findAll(pageable).map(
				
				
				user -> UserListDto.builder()
				.no(user.getNo())
				.email(user.getEmail())
				.nickname(user.getNickname())
				.gender(user.getGender())
				.birthDay(user.getBirthDay())
				.userType(user.getUserType())
				.matchcount(meetingLogRepository.countByUser1No(user.getNo()))
				.reportcount(reportRepository.countByUserNo(user.getNo()))
				.build()
				);
				
		return users;
	}

	@Override
	public Page<UserReportListDto> getUserReports(Pageable pageable, int no) {
		Page<UserReportListDto> reports= reportRepository.findByReported(no, pageable).map(
				report -> UserReportListDto.builder()
				.no(report.getNo())
				.user_email(report.getUser().getEmail())
				.category(report.getCategory())
				.reportTime(report.getReportTime())
				.build()
				);
		return reports;
	}

	@Override
	public ReportDetailDto getReportDetail(int no) {
		Optional<Report> report = reportRepository.findById(no);
		if (!report.isPresent()) return null;
		Report reportDetail= report.get();
		ReportDetailDto dto = ReportDetailDto.builder()
				.no(reportDetail.getNo())
				.content(reportDetail.getContent())
				.build();
		return dto;
	}

	@Override
	public boolean BanUser(int no) {
		User user=userRepository.findById(no).get();
		user.setUserType("Banned");
		userRepository.save(user);
		return true;
	}

	@Override
	public boolean ModifyNickname(NicknameModifyRequestDto dto) {
		User user=userRepository.findById(dto.getNo()).get();
		user.setNickname(dto.getNickname());
		userRepository.save(user);
		return true;
	}

	@Override
	public boolean InquiryAnswer(InquiryAnswerRequestDto dto) {
		try {
			Inquiry inquiry=inquiryRepository.findById(dto.getNo()).get();
			inquiry.setAnswer(dto.getAnswer());
			inquiryRepository.save(inquiry);
		}catch(Exception e) {
			return false;
		}
		return true;
	}

}

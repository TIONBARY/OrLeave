package com.orleave.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orleave.dto.InquiryListDto;
import com.orleave.dto.ReportDetailDto;
import com.orleave.dto.UserListDto;
import com.orleave.dto.UserReportListDto;
import com.orleave.dto.request.InquiryAnswerRequestDto;
import com.orleave.dto.request.NicknameModifyRequestDto;
import com.orleave.dto.request.NoticeModifyRequestDto;
import com.orleave.dto.request.NoticeRequestDto;
import com.orleave.entity.Inquiry;
import com.orleave.entity.Notice;
import com.orleave.entity.Report;
import com.orleave.entity.User;
import com.orleave.exception.InquiryNotFoundException;
import com.orleave.exception.NoticeNotFoundException;
import com.orleave.exception.ReportNotFoundException;
import com.orleave.exception.UserNotFoundException;
import com.orleave.repository.InquiryRepository;
import com.orleave.repository.MeetingLogRepository;
import com.orleave.repository.NoticeRepository;
import com.orleave.repository.ReportRepository;
import com.orleave.repository.UserRepository;

@Service("ManagerService")
public class ManagerServiceImpl implements ManagerService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	InquiryRepository inquiryRepository;
	
	@Autowired
	NoticeRepository noticeRepository;
	
	@Autowired
	MeetingLogRepository meetingLogRepository;
	
	@Autowired
	ReportRepository reportRepository;
	
	@Override
	public Page<UserListDto> getUsers(Pageable pageable){
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
	public Page<UserReportListDto> getUserReports(Pageable pageable, int no){
		Page<UserReportListDto> reports= reportRepository.findByReported(no, pageable).map(
				report -> UserReportListDto.builder()
				.no(report.getNo())
				.userEmail(report.getUser().getEmail())
				.category(report.getCategory())
				.reportTime(report.getReportTime())
				.build()
				);
		return reports;
	}

	@Override
	public ReportDetailDto getReportDetail(int no) throws Exception{
		Optional<Report> report = reportRepository.findById(no);
		if (!report.isPresent()) throw new ReportNotFoundException();
		Report reportDetail= report.get();
		ReportDetailDto dto = ReportDetailDto.builder()
				.no(reportDetail.getNo())
				.content(reportDetail.getContent())
				.build();
		return dto;
	}

	@Override
	public void BanUser(int no) throws Exception {
		Optional<User> usertemp=userRepository.findById(no);
		if(!usertemp.isPresent()) throw new UserNotFoundException();
		User user=usertemp.get();
		user.setUserType("Banned");
		userRepository.save(user);
		
	}

	@Override
	public void ModifyNickname(NicknameModifyRequestDto dto) throws Exception{
		Optional<User> usertemp=userRepository.findById(dto.getNo());
		if(!usertemp.isPresent()) throw new UserNotFoundException();
		User user=usertemp.get();
		if(dto.getNickname()==null) throw new IllegalArgumentException();
		user.setNickname(dto.getNickname());
		userRepository.save(user);
	}

	@Override
	public void InquiryAnswer(InquiryAnswerRequestDto dto) throws Exception{
		Optional<Inquiry> inquirytemp=inquiryRepository.findById(dto.getNo());
		if(!inquirytemp.isPresent()) throw new InquiryNotFoundException();
		Inquiry inquiry=inquirytemp.get();
		inquiry.setAnswer(dto.getAnswer());
		inquiryRepository.save(inquiry);		
	}

	@Override
	public void CreateNotices(NoticeRequestDto dto) throws Exception{
		if(dto.getContent() ==null || dto.getTitle()==null) throw new IllegalArgumentException();
		Notice notice=Notice.builder()
				.title(dto.getTitle())
				.content(dto.getContent())
				.createdTime(LocalDateTime.now())
				.build();
		noticeRepository.save(notice);
	}

	@Override
	public void ModifyNotices(NoticeModifyRequestDto dto) throws Exception{
		Optional<Notice> noticetemp=noticeRepository.findById(dto.getNo());
		if(!noticetemp.isPresent())	throw new NoticeNotFoundException();	
		Notice notice=noticetemp.get();
		notice.setContent(dto.getContent());
		notice.setTitle(dto.getTitle());
		noticeRepository.save(notice);
	}

	@Override
	public void DeleteNotices(int no) throws Exception {
		if(noticeRepository.findById(no)==null) throw new IllegalArgumentException();
		noticeRepository.deleteById(no);
		
		
	}

	@Override
	public Page<InquiryListDto> getInquiries(Pageable pageable) {
		Page<InquiryListDto> inquiries = inquiryRepository.findAll(pageable).map(
				inquiry -> InquiryListDto.builder()
				.no(inquiry.getNo())
				.title(inquiry.getTitle())
				.isAnswered(inquiry.getAnswer().isEmpty())
				.createdTime(inquiry.getCreatedTime())
				.build()
				);
				
		return inquiries;
	}

}

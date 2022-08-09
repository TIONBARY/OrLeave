package com.orleave.service;


import java.time.LocalDateTime;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orleave.dto.InquiryDetailDto;
import com.orleave.dto.InquiryListDto;
import com.orleave.dto.request.InquiryRequestDto;
import com.orleave.entity.Inquiry;
import com.orleave.entity.User;
import com.orleave.exception.UserNotFoundException;
import com.orleave.repository.InquiryRepository;
import com.orleave.repository.UserRepository;


@Service
public class InquiryServiceImpl implements InquiryService{
	@Autowired
	InquiryRepository inquiryRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public Page<InquiryListDto> getInquiriesByUserNo(int userNo, Pageable pageable) throws Exception {
		Page<InquiryListDto> inquiryList = inquiryRepository.findByUserNo(userNo, pageable)
				.map(inquiry -> InquiryListDto.builder()
						.no(inquiry.getNo())
						.title(inquiry.getTitle())
						.createdTime(inquiry.getCreatedTime())
						.build());	
		return inquiryList;
	}

	@Override
	@Transactional
	public boolean createInquiry(User user, InquiryRequestDto inquiryRequestDto) throws Exception {
		Inquiry inquiry = Inquiry.builder()
				.title(inquiryRequestDto.getTitle())
				.content(inquiryRequestDto.getContent())
				.createdTime(LocalDateTime.now())
				.answer("")
				.user(user)
				.build();
		inquiryRepository.save(inquiry);
		return true;
	}

	@Override
	@Transactional
	public InquiryDetailDto getInquiryDetail(int no, int userNo) throws Exception {
		Optional<Inquiry> inquiryTemp = inquiryRepository.findById(no);
		if (!inquiryTemp.isPresent()) return null;
		Inquiry inquiry = inquiryTemp.get();
		if(inquiry.getUser().getNo()!=userNo) throw new UserNotFoundException();
		InquiryDetailDto dto = InquiryDetailDto.builder()
				.no(inquiry.getNo())
				.title(inquiry.getTitle())
				.content(inquiry.getContent())
				.answer(inquiry.getAnswer())
				.createdTime(inquiry.getCreatedTime())
				.build();
		return dto;
	}

	@Override
	@Transactional
	public boolean modifyInquiry(int no, int userNo, InquiryRequestDto inquiryRequestDto) throws Exception {
		Optional<Inquiry> inquiryTemp = inquiryRepository.findById(no);
		if (!inquiryTemp.isPresent()) return false;
		Inquiry inquiry = inquiryTemp.get();
		if(inquiry.getUser().getNo()!=userNo) throw new UserNotFoundException();
		inquiry.setTitle(inquiryRequestDto.getTitle());
		inquiry.setContent(inquiryRequestDto.getContent());
		inquiryRepository.save(inquiry);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteInquiry(int no, int userNo) throws Exception {
		Optional<Inquiry> inquiryTemp = inquiryRepository.findById(no);
		if (!inquiryTemp.isPresent()) return false;
		Inquiry inquiry = inquiryTemp.get();
		if(inquiry.getUser().getNo()!=userNo) throw new UserNotFoundException();
		inquiryRepository.deleteById(no);
		return true;
	}
	
}

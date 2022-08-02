package com.orleave.service;


import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orleave.dto.InquiryListDto;
import com.orleave.dto.NoticeListDto;
import com.orleave.dto.request.InquiryRequestDto;
import com.orleave.entity.Inquiry;
import com.orleave.entity.User;
import com.orleave.repository.InquiryRepository;

import lombok.Builder;

@Service
public class InquiryServiceImpl implements InquiryService{
	@Autowired
	InquiryRepository inquiryRepository;

	@Override
	public Page<InquiryListDto> getInquiriesByUserNo(int userNo, Pageable pageable) {
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
	public boolean createInquiry(User user, InquiryRequestDto inquiryRequestDto) {
		Inquiry inquiry = Inquiry.builder()
				.title(inquiryRequestDto.getTitle())
				.context(inquiryRequestDto.getContent())
				.createdTime(LocalDateTime.now())
				.answer("")
				.user(user)
				.build();
		inquiryRepository.save(inquiry);
		user.addInquiry(inquiry);
		return true;
	}
	
}

package com.orleave.service;


import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orleave.dto.request.InquiryRequestDto;
import com.orleave.entity.Inquiry;
import com.orleave.entity.User;
import com.orleave.repository.InquiryRepository;

@Service
public class InquiryServiceImpl implements InquiryService{
	@Autowired
	InquiryRepository inquiryRepository;

	@Override
	public Page<Inquiry> getInquiriesByUserNo(User user, Pageable pageable) {
		Page<Inquiry> inquiryList = inquiryRepository.findByUser(user, pageable);
//		List<Inquiry> inquiryList = user.getInquiries();
//		int start = (int)pageable.getOffset();
//		int end = (start + pageable.getPageSize()) > inquiryList.size() ? inquiryList.size() : (start + pageable.getPageSize());
//		Page<Inquiry> page = new PageImpl<Inquiry>(inquiryList.subList(start, end), pageable,inquiryList.size());
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

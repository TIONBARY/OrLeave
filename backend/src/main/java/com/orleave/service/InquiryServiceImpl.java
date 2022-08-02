package com.orleave.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orleave.entity.Inquiry;
import com.orleave.repository.InquiryRepository;

@Service
public class InquiryServiceImpl implements InquiryService{
	@Autowired
	InquiryRepository inquiryRepository;

	@Override
	public Page<Inquiry> getInquiriesByUserNo(int userNo, Pageable pageable) {
		Page<Inquiry> inquiryList = inquiryRepository.findByUserNo(userNo, pageable);
		return inquiryList;
	}
	
}

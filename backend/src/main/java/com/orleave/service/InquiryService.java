package com.orleave.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orleave.entity.Inquiry;
/**
 *	1:1문의 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface InquiryService {
	Page<Inquiry> getInquiriesByUserNo(int userNo, Pageable pageable);
}

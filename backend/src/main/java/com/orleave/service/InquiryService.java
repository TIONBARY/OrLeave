package com.orleave.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orleave.dto.InquiryDetailDto;
import com.orleave.dto.InquiryListDto;
import com.orleave.dto.request.InquiryRequestDto;
import com.orleave.entity.User;
/**
 *	1:1문의 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface InquiryService {
	Page<InquiryListDto> getInquiriesByUserNo(int userNo, Pageable pageable);
	boolean createInquiry(User user, InquiryRequestDto inquiryRequestDto);
	InquiryDetailDto getInquiryDetail(int no, int userNo);
	boolean modifyInquiry(int no, int userNo, InquiryRequestDto inquiryRequestDto);
}

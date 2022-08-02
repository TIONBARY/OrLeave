package com.orleave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orleave.auth.SsafyUserDetails;
import com.orleave.dto.response.InquiryListResponseDto;
import com.orleave.entity.Inquiry;
import com.orleave.service.InquiryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "1:1문의 API", tags = {"Inquiry"})
@RestController
@RequestMapping("/api/v1/inquires")
public class InquiryController {
	
	@Autowired
	InquiryService inquiryService;
	
	@GetMapping("")
	@ApiOperation(value = "내 1:1문의 조회", notes = "사용자의 1:1문의를 페이지 정보에 따라 전체 조회한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "1:1문의 조회 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<InquiryListResponseDto> getInquiresByUserNo (
			@ApiIgnore Authentication authentication, Pageable pageable) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int userNo = userDetails.getUser().getNo();
		Page<Inquiry> inquiryList = inquiryService.getInquiriesByUserNo(userNo, pageable);
		return ResponseEntity.status(200).body(InquiryListResponseDto.of(200, "Success", inquiryList));
	}
}

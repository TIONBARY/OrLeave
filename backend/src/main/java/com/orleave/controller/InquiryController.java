package com.orleave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orleave.auth.SsafyUserDetails;
import com.orleave.dto.InquiryDetailDto;
import com.orleave.dto.InquiryListDto;
import com.orleave.dto.request.InquiryRequestDto;
import com.orleave.dto.response.BaseResponseDto;
import com.orleave.dto.response.InquiryDetailResponseDto;
import com.orleave.dto.response.InquiryListResponseDto;
import com.orleave.entity.User;
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
			@ApiIgnore Authentication authentication, @RequestParam("page") int page, @RequestParam("size") int size) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int userNo = userDetails.getUser().getNo();
		Page<InquiryListDto> inquiryList = inquiryService.getInquiriesByUserNo(userNo, PageRequest.of(page, size, Sort.by("no").descending()));
		return ResponseEntity.status(200).body(InquiryListResponseDto.of(200, "Success", inquiryList));
	}
	@PostMapping("")
	@ApiOperation(value = "1:1문의 작성", notes = "1:1문의를 생성한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "1:1문의 생성 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> createInquiry (
			@ApiIgnore Authentication authentication, InquiryRequestDto inquiryRequestDto) {
		try {
			SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
			User user = userDetails.getUser();
			if (inquiryService.createInquiry(user, inquiryRequestDto)) {
				return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Created"));
			} else {
				return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Failed"));
			}
			
		} catch (NullPointerException e) {
			return ResponseEntity.status(403).body(BaseResponseDto.of(403, "Forbidden"));
		}
	}
	@GetMapping("/{no}")
	@ApiOperation(value = "1:1문의 상세 조회", notes = "1:1문의 상세 정보를 조회한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "1:1문의 조회 실패"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> getInquiryDetail(
			@ApiIgnore Authentication authentication, @PathVariable("no") int no) {
		try {
			SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
			int userNo = userDetails.getUser().getNo();
			InquiryDetailDto inquiry = inquiryService.getInquiryDetail(no, userNo);
			return ResponseEntity.status(200).body(InquiryDetailResponseDto.of(200, "Success", inquiry));
		} catch (NullPointerException e) {
			return ResponseEntity.status(403).body(BaseResponseDto.of(403, "Forbidden"));
		}
	}
}

package com.orleave.controller;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> getInquiresByUserNo (
			@ApiIgnore Authentication authentication, @RequestParam("page") int page, @RequestParam("size") int size) {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
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
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> createInquiry (
			@ApiIgnore Authentication authentication, @RequestBody InquiryRequestDto inquiryRequestDto) {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		User user = userDetails.getUser();
		if (inquiryService.createInquiry(user, inquiryRequestDto)) {
			return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Created"));
		} else {
			return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Failed"));
		}
	}
	@GetMapping("/{no}")
	@ApiOperation(value = "1:1문의 상세 조회", notes = "1:1문의 상세 정보를 조회한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 403, message = "접근 권한 없음"),
        @ApiResponse(code = 404, message = "1:1문의 조회 실패"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> getInquiryDetail(
			@ApiIgnore Authentication authentication, @PathVariable("no") int no) {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int userNo = userDetails.getUser().getNo();
		try {
			InquiryDetailDto inquiry = inquiryService.getInquiryDetail(no, userNo);
			return ResponseEntity.status(200).body(InquiryDetailResponseDto.of(200, "Success", inquiry));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(403).body(BaseResponseDto.of(403, "Forbidden"));
		}
	}
	@PutMapping("/{no}")
	@ApiOperation(value = "1:1문의 수정", notes = "1:1문의를 수정한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 403, message = "접근 권한 없음"),
        @ApiResponse(code = 404, message = "1:1문의 수정 실패"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> modifyInquiry(
			@ApiIgnore Authentication authentication, @PathVariable("no") int no, @RequestBody InquiryRequestDto inquiryRequestDto) {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int userNo = userDetails.getUser().getNo();
		try {
			if(inquiryService.modifyInquiry(no, userNo, inquiryRequestDto)) {
				return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Modified"));
			} else {
				return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Failed"));
			}
		} catch (AuthenticationException e) {
			return ResponseEntity.status(403).body(BaseResponseDto.of(403, "Forbidden"));
		}
		
	}
	@DeleteMapping("/{no}")
	@ApiOperation(value = "1:1문의 삭제", notes = "1:1문의를 삭제한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 403, message = "접근 권한 없음"),
        @ApiResponse(code = 404, message = "1:1문의 삭제 실패"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> deleteInquiry(
			@ApiIgnore Authentication authentication, @PathVariable("no") int no) {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int userNo = userDetails.getUser().getNo();
		try {
			if(inquiryService.deleteInquiry(no, userNo)) {
				return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Deleted"));
			} else {
				return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Failed"));
			}
		} catch (AuthenticationException e) {
			return ResponseEntity.status(403).body(BaseResponseDto.of(403, "Forbidden"));
		}
	}
}

package com.orleave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orleave.dto.NoticeDetailDto;
import com.orleave.dto.NoticeListDto;
import com.orleave.dto.response.NoticeDetailResponseDto;
import com.orleave.dto.response.NoticeListResponseDto;
import com.orleave.service.NoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "공지사항 API", tags = {"Notice"})
@RestController
@RequestMapping("/api/v1/notices")
public class NoticeController {
	
	@Autowired
	NoticeService noticeService;
	
	@GetMapping("")
	@ApiOperation(value = "공지사항 전체 조회", notes = "공지사항을 페이지 정보에 따라 전체 조회한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "공지사항 조회 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<NoticeListResponseDto> getAllNotices(@RequestParam("page") int page, @RequestParam("size") int size) {
		Page<NoticeListDto> noticeList = noticeService.getNotices(PageRequest.of(page, size, Sort.by("no").descending()));
		return ResponseEntity.status(200).body(NoticeListResponseDto.of(200, "Success", noticeList));
	}
	
	@GetMapping("/{no}")
	@ApiOperation(value = "공지사항 전체 조회", notes = "공지사항을 페이지 정보에 따라 전체 조회한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "공지사항 조회 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<NoticeDetailResponseDto> getNoticeDetail(@PathVariable("no") int no) {
		NoticeDetailDto notice = noticeService.getNoticeDetail(no);		
		return ResponseEntity.status(200).body(NoticeDetailResponseDto.of(200, "Success", notice));
	}
}

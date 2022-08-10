package com.orleave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orleave.auth.SsafyUserDetails;
import com.orleave.dto.BanListDto;
import com.orleave.dto.request.BanRequestDto;
import com.orleave.dto.response.BanListResponseDto;
import com.orleave.dto.response.BaseResponseDto;
import com.orleave.entity.User;
import com.orleave.service.BanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "차단 API", tags = {"Ban"})
@RestController
@RequestMapping("/api/v1/ban")
public class BanController {
	
	@Autowired
	BanService banService;
	
	@GetMapping("")
	@ApiOperation(value = "내 차단 목록 조회", notes = "사용자가 차단한 유저의 전체 목록을 조회한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> getBansByUserNo (
			@ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int userNo = userDetails.getUserno();
		List<BanListDto> banList = banService.findByUserNo(userNo);
		return ResponseEntity.status(200).body(BanListResponseDto.of(200, "Success", banList));
	}
	
	@PostMapping("")
	@ApiOperation(value = "차단 추가", notes = "대상 유저를 차단한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "부적절한 입력"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> createBan (
			@ApiIgnore Authentication authentication, @RequestBody @ApiParam(value="차단 회원", required = true) BanRequestDto banRequestDto) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		User user = userDetails.getUser();
		banService.createBan(user, banRequestDto.getBannedNo());
		return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Created"));
	}
	
	@DeleteMapping("/{bannedNo}")
	@ApiOperation(value = "차단 취소", notes = "대상 유저의 차단을 취소한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "부적절한 입력"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> deleteBan (
			@ApiIgnore Authentication authentication, @PathVariable("bannedNo") int bannedNo) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		User user = userDetails.getUser();
		banService.deleteBan(user, bannedNo);
		return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Deleted"));
	}
}

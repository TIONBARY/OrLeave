package com.orleave.controller;

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
import com.orleave.dto.WaitingUserDto;
import com.orleave.dto.request.LocationRequestDto;
import com.orleave.dto.response.BaseResponseDto;
import com.orleave.dto.response.MatchingResponseDto;
import com.orleave.service.MatchingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "매칭 API", tags = {"Matching"})
@RestController
@RequestMapping("/api/v1/matching")
public class MatchingController {
	
	@Autowired
	MatchingService matchingService;
	
	@PostMapping("")
	@ApiOperation(value = "매칭 시작하기", notes = "매칭을 시작한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 403, message = "액세스 토큰 없음"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<BaseResponseDto> startMatching(@RequestBody @ApiParam(value="현재 위치", required = true) LocationRequestDto locationRequestDto,
			@ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int userNo = userDetails.getUserNo();
		matchingService.startMatching(userNo, locationRequestDto.getLat(), locationRequestDto.getLng());
		return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
	}
	
	@GetMapping("")
	@ApiOperation(value = "매칭 확인하기", notes = "매칭을 확인한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "매칭된 상대 없음"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 403, message = "액세스 토큰 없음"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> findMatching(@ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int userNo = userDetails.getUserNo();
		WaitingUserDto matchedUser = matchingService.findMatching(userNo);
		if (matchedUser == null) return ResponseEntity.status(400).body(BaseResponseDto.of(400, "User Not Exist"));
		return ResponseEntity.status(200).body(MatchingResponseDto.of(200, "Success", matchedUser));
	}
	
	@GetMapping("/stop")
	@ApiOperation(value = "매칭 취소하기", notes = "매칭을 취소한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "매칭된 상대 없음"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 403, message = "액세스 토큰 없음"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> stopMatching(@ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int userNo = userDetails.getUserNo();
		matchingService.stopMatching(userNo);
		return ResponseEntity.status(200).body(MatchingResponseDto.of(200, "Success"));
	}
	
	@GetMapping("/success/{no}")
	@ApiOperation(value = "매칭 성사", notes = "매칭을 양쪽이 수락했을 경우 남자 쪽에서 여성 쪽 데이터를 가져온다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "매칭된 상대 없음"),
        @ApiResponse(code = 401, message = "인증되지 않은 토큰"),
        @ApiResponse(code = 403, message = "액세스 토큰 없음"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> MatchingSuccess(@PathVariable("no") @ApiParam(value="상대 유저의 번호", required = true) int femaleNo,
			@ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int userNo = userDetails.getUserNo();
		WaitingUserDto femaleDto = matchingService.matchingSuccess(userNo, femaleNo);
		return ResponseEntity.status(200).body(MatchingResponseDto.of(200, "Success", femaleDto));
	}
}

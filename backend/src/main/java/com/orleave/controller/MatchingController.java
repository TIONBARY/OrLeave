package com.orleave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
        @ApiResponse(code = 403, message = "액세스 토큰 없음"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<BaseResponseDto> getMeeingSetting(@ApiIgnore Authentication authentication, 
			@RequestBody @ApiParam(value="현재 위치", required = true) LocationRequestDto locationRequestDto) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int no = userDetails.getUserno();
		matchingService.startMatching(no, locationRequestDto.getLat(), locationRequestDto.getLng());
		return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
	}
	
	@GetMapping("")
	@ApiOperation(value = "매칭 확인하기", notes = "매칭을 확인한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "매칭된 상대 없음"),
        @ApiResponse(code = 403, message = "액세스 토큰 없음"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> getMeeingSetting(@ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int no = userDetails.getUserno();
		WaitingUserDto matchedUser = matchingService.findMatching(no);
		if (matchedUser == null) return ResponseEntity.status(400).body(BaseResponseDto.of(400, "User Not Exist"));
		return ResponseEntity.status(200).body(MatchingResponseDto.of(200, "Success", matchedUser));
	}
}

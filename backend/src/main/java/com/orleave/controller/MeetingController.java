package com.orleave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.orleave.auth.SsafyUserDetails;
import com.orleave.dto.MeetingLogListDto;
import com.orleave.dto.request.LocationRequestDto;
import com.orleave.dto.request.MeetingSettingRequestDto;
import com.orleave.dto.request.ReportRequestDto;
import com.orleave.dto.response.BaseResponseDto;
import com.orleave.dto.response.MeetingLogResponseDto;
import com.orleave.dto.response.MeetingSettingResponseDto;
import com.orleave.entity.MeetingSetting;
import com.orleave.entity.User;
import com.orleave.service.MeetingService;
import com.orleave.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "미팅 API", tags = {"Meeting"})
@RestController
@RequestMapping("/api/v1/meeting")
public class MeetingController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MeetingService meetingService;
	
	@GetMapping("/setting")
	@ApiOperation(value = "미팅 설정 가져오기", notes = "본인의 미팅 설정값을 가져온다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 403, message = "액세스 토큰 없음"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<MeetingSettingResponseDto> getMeeingSetting(@ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(MeetingSettingResponseDto.of(401, "Unauthorized",null));
		
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int no = userDetails.getUserno();
		MeetingSetting meetingsetting = meetingService.getSettingByNo(no);
		
		
		
		return ResponseEntity.status(200).body(MeetingSettingResponseDto.of(200,"sucess",meetingsetting));
	}
	
	
	@PutMapping("/setting")
	@ApiOperation(value = "미팅 설정 변경하기", notes = "본인의 미팅 설정값을 변경한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 403, message = "액세스 토큰 없음"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> ModifyMeeingSetting(
			@RequestBody @ApiParam(value="미팅설정", required = true) MeetingSettingRequestDto meetingSettingRequestDto,
			@ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));

		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int no = userDetails.getUserno();
		
		meetingService.modifyMeetingSetting(no, meetingSettingRequestDto);
			return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Modified"));
	}
	
	@GetMapping("/recent-call")
	@ApiOperation(value = "최근 미팅 목록 가져오기", notes = "본인의 일주일 동안의 미팅 목록을 가져온다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 403, message = "액세스 토큰 없음"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseDto> getMeeingLogs(@ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));
		
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int no = userDetails.getUserno();
		List<MeetingLogListDto> meetingLogs = meetingService.getRecentMeetingLogs(no);
		return ResponseEntity.status(200).body(MeetingLogResponseDto.of(200, "Success", meetingLogs));
	}
	
	@PostMapping("")
	@ApiOperation(value = "신고하기", notes = "미팅 상대를 신고한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 403, message = "액세스 토큰 없음"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<BaseResponseDto> report(@RequestBody @ApiParam(value="신고 내용", required = true) ReportRequestDto reportRequestDto,
			@ApiIgnore Authentication authentication) throws Exception {
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseDto.of(401, "Unauthorized"));

		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		User user = userDetails.getUser();
		meetingService.reportUser(user, reportRequestDto);
		return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Created"));
	}

}

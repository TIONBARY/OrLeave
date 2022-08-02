package com.orleave.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orleave.auth.SsafyUserDetails;
import com.orleave.dto.request.MeetingSettingRequestDto;
import com.orleave.dto.request.ProfileModifyRequestDto;
import com.orleave.dto.response.BaseResponseDto;
import com.orleave.dto.response.MeetingSettingResponseDto;
import com.orleave.entity.MeetingSetting;
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
	public ResponseEntity<MeetingSettingResponseDto> getMeeingSetting(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int no = userDetails.getUserno();
		MeetingSetting meetingsetting = meetingService.getSettingByNo(no);
		
		
		
		return ResponseEntity.status(200).body(MeetingSettingResponseDto.of(meetingsetting));
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
			@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		int no = userDetails.getUserno();
		
		if (meetingService.modifyMeetingSetting(no, meetingSettingRequestDto)) {
			return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Modified"));
		} else {
			return ResponseEntity.status(400).body(BaseResponseDto.of(400, "Failed"));
		}
	}

}

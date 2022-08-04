package com.orleave.dto.response;

import com.orleave.dto.WaitingUserDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("MatchingResponse")
public class MatchingResponseDto extends BaseResponseDto {
	@ApiModelProperty(name="매칭된 상대방")
	WaitingUserDto maleDto;
	
	public static MatchingResponseDto of(Integer statusCode, String message, WaitingUserDto maleDto) {
		MatchingResponseDto res = new MatchingResponseDto();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setMaleDto(maleDto);
		return res;
	}
}

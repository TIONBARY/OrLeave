package com.orleave.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ApiModel("MeetingLogListDto")
public class MeetingLogListDto {
	@ApiModelProperty(name="번호")
	int no;
	@ApiModelProperty(name="매칭 상대방 번호")
	int userNo;
	@ApiModelProperty(name="매칭 상대방 닉네임")
	String nickname;
	@ApiModelProperty(name="최근 통화 시간")
	LocalDateTime createdTime;
}

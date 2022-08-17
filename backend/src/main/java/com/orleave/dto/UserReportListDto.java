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
@ApiModel("UserReportListDto")
public class UserReportListDto {
	
	@ApiModelProperty(name="신고번호")
	int no;
	@ApiModelProperty(name="신고한 사람")
	String userEmail;
	@ApiModelProperty(name="신고 사유")
	int category;
	@ApiModelProperty(name="신고한 시간")
	LocalDateTime reportTime;
}

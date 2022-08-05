package com.orleave.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.orleave.entity.Inquiry;
import com.orleave.entity.User;

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
	String user_email;
	@ApiModelProperty(name="신고 사유")
	int category;
	@ApiModelProperty(name="신고한 시간")
	LocalDateTime reportTime;
}

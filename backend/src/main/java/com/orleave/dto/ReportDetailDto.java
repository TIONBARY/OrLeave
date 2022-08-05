package com.orleave.dto;

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
@ApiModel("ReportDetailDto")
public class ReportDetailDto {
	@ApiModelProperty(name="신고 번호")
	int no;
	@ApiModelProperty(name="신고 내용")
	String content;

}

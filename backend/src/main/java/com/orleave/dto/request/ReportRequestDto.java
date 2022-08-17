package com.orleave.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ReportRequest")
public class ReportRequestDto {
	@ApiModelProperty(name="reportedNo", example="2")
	int reportedNo;
	@ApiModelProperty(name="category", example="2")
	int category;
	@ApiModelProperty(name="content", example="신고 내용")
	String content;
}

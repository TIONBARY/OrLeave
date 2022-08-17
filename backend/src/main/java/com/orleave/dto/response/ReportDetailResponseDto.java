package com.orleave.dto.response;

import com.orleave.dto.ReportDetailDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("ReportDetailResponse")
public class ReportDetailResponseDto extends BaseResponseDto{
	
	@ApiModelProperty(name="Report Detail")
	ReportDetailDto report;
	
	public static ReportDetailResponseDto of(Integer statusCode, String message, ReportDetailDto report) {
		ReportDetailResponseDto res = new ReportDetailResponseDto(statusCode, message, report);
		return res;
	}

	ReportDetailResponseDto(Integer statusCode, String message, ReportDetailDto report) {
		super(statusCode, message);
		this.report = report;
	}
}

	


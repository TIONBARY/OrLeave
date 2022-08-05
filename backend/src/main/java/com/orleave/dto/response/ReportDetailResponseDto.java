package com.orleave.dto.response;

import com.orleave.dto.NoticeDetailDto;
import com.orleave.dto.ReportDetailDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("ReportDetailResponse")
public class ReportDetailResponseDto extends BaseResponseDto{
	@ApiModelProperty(name="Report Detail")
	ReportDetailDto report;
	
	public static ReportDetailResponseDto of(Integer statusCode, String message, ReportDetailDto report) {
		ReportDetailResponseDto res = new ReportDetailResponseDto();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setReport(report);
		return res;
	}
}

	


package com.orleave.dto.response;

import com.orleave.dto.InquiryDetailDto;
import com.orleave.dto.NoticeDetailDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("InquiryDetailResponse")
public class InquiryDetailResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="Inquiry Detail")
	InquiryDetailDto inquiry;
	
	public static InquiryDetailResponseDto of(Integer statusCode, String message, InquiryDetailDto inquiry) {
		InquiryDetailResponseDto res = new InquiryDetailResponseDto();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setInquiry(inquiry);
		return res;
	}
}

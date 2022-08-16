package com.orleave.dto.response;

import org.springframework.data.domain.Page;

import com.orleave.dto.InquiryListDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("InquiryListResponse")
public class InquiryListResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="Inquiry List")
	Page<InquiryListDto> inquiryList;
	
	public static InquiryListResponseDto of(Integer statusCode, String message, Page<InquiryListDto> inquiryList) {
		InquiryListResponseDto res = new InquiryListResponseDto(statusCode, message, inquiryList);
		return res;
	}

	public InquiryListResponseDto(Integer statusCode, String message, Page<InquiryListDto> inquiryList) {
		super(statusCode, message);
		this.inquiryList = inquiryList;
	}
}

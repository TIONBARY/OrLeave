package com.orleave.dto.response;

import org.springframework.data.domain.Page;

import com.orleave.dto.NoticeListDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("NoticeListResponse")
public class NoticeListResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="Notice List")
	Page<NoticeListDto> noticeList;
	
	public static NoticeListResponseDto of(Integer statusCode, String message, Page<NoticeListDto> noticeList) {
		NoticeListResponseDto res = new NoticeListResponseDto(statusCode, message, noticeList);
		return res;
	}
	public NoticeListResponseDto(Integer statusCode, String message, Page<NoticeListDto> noticeList) {
		super(statusCode, message);
		this.noticeList = noticeList;
	}
}

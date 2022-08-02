package com.orleave.dto.response;

import org.springframework.data.domain.Page;

import com.orleave.entity.Notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("NoticeListResponse")
public class NoticeListResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="Notice List")
	Page<Notice> noticeList;
	
	public static NoticeListResponseDto of(Integer statusCode, String message, Page<Notice> noticeList) {
		NoticeListResponseDto res = new NoticeListResponseDto();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setNoticeList(noticeList);
		return res;
	}
}

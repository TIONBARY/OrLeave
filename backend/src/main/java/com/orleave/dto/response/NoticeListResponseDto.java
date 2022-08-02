package com.orleave.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.orleave.dto.NoticeDto;
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
	List<NoticeDto> noticeList;
	
	public static NoticeListResponseDto of(Integer statusCode, String message, List<NoticeDto> noticeList) {
		NoticeListResponseDto res = new NoticeListResponseDto();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setNoticeList(noticeList);
		return res;
	}
}

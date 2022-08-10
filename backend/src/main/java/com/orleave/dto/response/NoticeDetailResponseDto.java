package com.orleave.dto.response;

import com.orleave.dto.NoticeDetailDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("NoticeDetailResponse")
public class NoticeDetailResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="Notice Detail")
	NoticeDetailDto notice;
	
	public static NoticeDetailResponseDto of(Integer statusCode, String message, NoticeDetailDto notice) {
		NoticeDetailResponseDto res = new NoticeDetailResponseDto();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setNotice(notice);
		return res;
	}

	
}

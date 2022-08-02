package com.orleave.dto;

import java.time.LocalDateTime;

import com.orleave.entity.Notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("NoticeDto")
public class NoticeDto {
	@ApiModelProperty(name="Notice No")
	int no;
	@ApiModelProperty(name="Notice Title")
	String title;
	@ApiModelProperty(name="Notice Created Time")
	LocalDateTime createdTime;
	
	public static NoticeDto of(Notice notice) {
		NoticeDto res = new NoticeDto();
		res.setNo(notice.getNo());
		res.setTitle(notice.getTitle());
		res.setCreatedTime(notice.getCreatedTime());
		return res;
	}
}

package com.orleave.dto;

import java.time.LocalDateTime;

import com.orleave.entity.Notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ApiModel("NoticeListDto")
public class NoticeListDto {
	
	@ApiModelProperty(name="공지사항 번호")
	int no;
	@ApiModelProperty(name="공지사항 제목")
	String title;
	@ApiModelProperty(name="공지사항 작성 시간")
	LocalDateTime createdTime;
	
	public static NoticeListDto of(Notice notice) {
		NoticeListDto res = NoticeListDto.builder()
				.no(notice.getNo())
				.title(notice.getTitle())
				.createdTime(notice.getCreatedTime())
				.build();
		return res;
	}
}

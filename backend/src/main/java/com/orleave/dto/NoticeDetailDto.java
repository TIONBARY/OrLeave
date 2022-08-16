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
@ApiModel("NoticeDetailDto")
public class NoticeDetailDto {
	
	@ApiModelProperty(name="공지사항 번호")
	int no;
	@ApiModelProperty(name="공지사항 제목")
	String title;
	@ApiModelProperty(name="공지사항 내용")
	String content;
	@ApiModelProperty(name="공지사항 작성 시간")
	LocalDateTime createdTime;
	
	public static NoticeDetailDto of(Notice notice) {
		NoticeDetailDto res = NoticeDetailDto.builder()
				.no(notice.getNo())
				.title(notice.getTitle())
				.content(notice.getContent())
				.createdTime(notice.getCreatedTime())
				.build();
		return res;
	}
}

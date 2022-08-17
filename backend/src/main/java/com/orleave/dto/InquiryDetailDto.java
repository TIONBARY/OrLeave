package com.orleave.dto;

import java.time.LocalDateTime;

import com.orleave.entity.Inquiry;

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
@ApiModel("InquiryListDto")
public class InquiryDetailDto {
	
	@ApiModelProperty(name="번호")
	int no;
	@ApiModelProperty(name="문의 제목")
	String title;
	@ApiModelProperty(name="문의 내용")
	String content;
	@ApiModelProperty(name="문의 답변")
	String answer;
	@ApiModelProperty(name="문의 작성 시간")
	LocalDateTime createdTime;
	
	public static InquiryDetailDto of(Inquiry inquiry) {
		InquiryDetailDto res = InquiryDetailDto.builder()
				.no(inquiry.getNo())
				.title(inquiry.getTitle())
				.content(inquiry.getContent())
				.answer(inquiry.getAnswer())
				.createdTime(inquiry.getCreatedTime())
				.build();
		return res;
	}
}

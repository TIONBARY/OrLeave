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
public class InquiryListDto {
	
	@ApiModelProperty(name="번호")
	int no;
	@ApiModelProperty(name="문의 제목")
	String title;
	@ApiModelProperty(name="문의 답변 여부")
	boolean isAnswered;
	@ApiModelProperty(name="문의 작성 시간")
	LocalDateTime createdTime;
	
	public static InquiryListDto of(Inquiry inquiry) {
		InquiryListDto res = InquiryListDto.builder()
				.no(inquiry.getNo())
				.title(inquiry.getTitle())
				.isAnswered(inquiry.getAnswer().length() > 0)
				.createdTime(inquiry.getCreatedTime())
				.build();
		return res;
	}
}

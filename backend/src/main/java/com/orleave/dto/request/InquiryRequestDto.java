package com.orleave.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryRequestDto {
	@ApiModelProperty(name="1:1문의 제목", example="문의 제목")
	String title;
	@ApiModelProperty(name="1:1문의 내용", example="문의 내용")
	String content;
}

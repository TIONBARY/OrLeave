package com.orleave.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("NoticeModifyRequest")
public class NoticeModifyRequestDto {
	@ApiModelProperty(name="no")
	int no;
	@ApiModelProperty(name="title", example="공지 제목")
	String title;
	@ApiModelProperty(name="content", example="공지 내용")
	String content;
}

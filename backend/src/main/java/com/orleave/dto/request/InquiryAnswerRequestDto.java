package com.orleave.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("InquiryAnswerRequestDto")
public class InquiryAnswerRequestDto {
	@ApiModelProperty(name="inquiryNo")
	int no;
	@ApiModelProperty(name="answer")
	String Answer;
}

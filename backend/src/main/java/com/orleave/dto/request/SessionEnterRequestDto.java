package com.orleave.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("SessionRequest")
public class SessionEnterRequestDto {
	@ApiModelProperty(name="sessionName", example="Session 1")
	String sessionName;
}

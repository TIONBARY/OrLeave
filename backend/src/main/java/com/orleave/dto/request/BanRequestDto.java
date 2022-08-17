package com.orleave.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("BanRequest")
public class BanRequestDto {
	@ApiModelProperty(name="bannedNo", example="2")
	int bannedNo;
}

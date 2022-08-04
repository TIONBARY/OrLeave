package com.orleave.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("LocationRequest")
public class LocationRequestDto {
	@ApiModelProperty(name="lat", example="37.5")
	double lat;
	@ApiModelProperty(name="lng", example="127.5")
	double lng;
}

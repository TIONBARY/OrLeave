package com.orleave.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel("MeetingSettingDto")
public class MeetingSettingDto {
	
	@ApiModelProperty(name="종교")
	int religion;
	@ApiModelProperty(name="흡연")
	int smoke;
	@ApiModelProperty(name="최소 음주량")
	int drinkMin;
	@ApiModelProperty(name="최대 음주량")
	int drinkMax;
	@ApiModelProperty(name="거리")
	int distance;
	@ApiModelProperty(name="최소 나이")
	int ageMin;
	@ApiModelProperty(name="최대 나이")
	int ageMax;
}

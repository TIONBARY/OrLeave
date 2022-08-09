package com.orleave.dto;


import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@ApiModel("MeetingSettingDto")
public class MeetingSettingDto {
	int religion;
	int smoke;
	int drink_min;
	int drink_max;
	int distance;
	int age_min;
	int age_max;
}

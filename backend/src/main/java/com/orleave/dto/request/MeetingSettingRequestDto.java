package com.orleave.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("MeetingSettingRequest")
public class MeetingSettingRequestDto {
	int religion;
	int smoke;
	int drink_min;
	int drink_max;
	int distance;
	int age_min;
	int age_max;

}

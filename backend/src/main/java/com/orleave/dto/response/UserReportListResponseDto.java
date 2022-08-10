package com.orleave.dto.response;

import org.springframework.data.domain.Page;

import com.orleave.dto.UserReportListDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserReportListResponse")
public class UserReportListResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="UserReport List")
	Page<UserReportListDto> userReportList;
	
	public static UserReportListResponseDto of(Integer statusCode, String message, Page<UserReportListDto> userReportList) {
		UserReportListResponseDto res = new UserReportListResponseDto();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setUserReportList(userReportList);
		return res;
	}
}

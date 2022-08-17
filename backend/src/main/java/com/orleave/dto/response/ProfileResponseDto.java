package com.orleave.dto.response;

import com.orleave.dto.ProfileDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 프로필 정보 조회 API ([GET] /api/v1/users/profile) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ProfileResponse")
public class ProfileResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="User Profile")
	ProfileDto profile;
	
	public static ProfileResponseDto of(Integer statusCode, String message, ProfileDto dto) {
		ProfileResponseDto res = new ProfileResponseDto(statusCode, message, dto);
		return res;
	}

	public ProfileResponseDto(Integer statusCode, String message, ProfileDto profile) {
		super(statusCode, message);
		this.profile = profile;
	}
}

package com.orleave.dto.response;

import org.springframework.data.domain.Page;

import com.orleave.dto.UserListDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserListResponse")
public class UserListResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="User List")
	Page<UserListDto> userList;
	
	public static UserListResponseDto of(Integer statusCode, String message, Page<UserListDto> userList) {
		UserListResponseDto res = new UserListResponseDto();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setUserList(userList);
		return res;
	}
}

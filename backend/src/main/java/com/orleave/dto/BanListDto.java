package com.orleave.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ApiModel("BanListDto")
public class BanListDto {
	
	@ApiModelProperty(name="번호")
	int no;
	@ApiModelProperty(name="차단한 사용자 번호")
	int bannedNo;
	@ApiModelProperty(name="차단한 사용자 닉네임")
	String bannedUserNickname;
}

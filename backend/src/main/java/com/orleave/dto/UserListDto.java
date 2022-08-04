package com.orleave.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.orleave.entity.Inquiry;
import com.orleave.entity.User;

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
@ApiModel("UserListDto")
public class UserListDto {
	@ApiModelProperty(name="번호")
	int no;
	@ApiModelProperty(name="이메일")
	String email;
	@ApiModelProperty(name="닉네임")
	String nickname;
	@ApiModelProperty(name="성별")
	String gender;
	@ApiModelProperty(name="생년월일")
	LocalDate birthday;
	@ApiModelProperty(name="매칭횟수")
	Long matchcount;
	@ApiModelProperty(name="신고당한횟수")
	Long reportcount;
	@ApiModelProperty(name="유저타입")
	String usertype;

	
	public static UserListDto of(User user) {
		UserListDto res = UserListDto.builder()
				.no(user.getNo())
				.email(user.getEmail())
				.nickname(user.getNickname())
				.gender(user.getGender())
				.birthday(user.getBirthDay())
				.usertype(user.getUserType())
				.build();
		return res;
	}
}

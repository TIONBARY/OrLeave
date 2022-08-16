package com.orleave.dto.message;

import java.time.LocalDateTime;

import com.orleave.dto.WaitingUserDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chat {
	String nickname;
	String content;
	String time;
}

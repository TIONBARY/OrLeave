package com.orleave.dto.message;

import com.orleave.dto.WaitingUserDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchingInfo {
	String roomId;
	int maleNo;
	WaitingUserDto female;
}

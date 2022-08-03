package com.orleave.service;

import com.orleave.dto.WaitingUserDto;

public interface MatchingService {
	void startMatching(int userNo, double lat, double lng);
	WaitingUserDto findMatching(int userNo);
}

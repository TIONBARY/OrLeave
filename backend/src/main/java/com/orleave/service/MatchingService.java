package com.orleave.service;

import com.orleave.dto.WaitingUserDto;
import com.orleave.exception.MatchingUserNotFoundException;

public interface MatchingService {
	void startMatching(int userNo, double lat, double lng) throws Exception;
	WaitingUserDto findMatching(int userNo) throws MatchingUserNotFoundException;
	void stopMatching(int userNo) throws MatchingUserNotFoundException;
	WaitingUserDto matchingSuccess(int userNo, int femaleNo) throws MatchingUserNotFoundException;
	WaitingUserDto getUserInfo(int userNo) throws MatchingUserNotFoundException;
}

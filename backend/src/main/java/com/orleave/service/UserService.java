package com.orleave.service;

import com.orleave.dto.request.SignupRequestDto;
import com.orleave.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(SignupRequestDto signupRequestDto);
	User getUserByEmail(String email);
	User getUserByNickname(String nickname);
}

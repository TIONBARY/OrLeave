package com.orleave.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.orleave.dto.request.ProfileModifyRequestDto;
import com.orleave.dto.request.SignupRequestDto;
import com.orleave.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	void createUser(SignupRequestDto signupRequestDto) throws Exception;
	User getUserByNo(int no) throws Exception;
	User getUserByEmail(String email) throws UsernameNotFoundException;
	User getUserByNickname(String nickname) throws Exception;
	void modifyProfile(int userNo, ProfileModifyRequestDto profileModifyRequestDto) throws Exception;
	void passwordcheck(int userNo,String password) throws Exception;
	void modifypassword(int userNo,String password) throws Exception;
	void deleteUser(User user) throws Exception;
	void loginfailed(int no) throws Exception;
	void logincheck(int no) throws Exception;
}

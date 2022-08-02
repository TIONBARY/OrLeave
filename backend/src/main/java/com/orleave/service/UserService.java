package com.orleave.service;

import com.orleave.dto.request.PasswordRequestDto;
import com.orleave.dto.request.ProfileModifyRequestDto;
import com.orleave.dto.request.SignupRequestDto;
import com.orleave.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(SignupRequestDto signupRequestDto);
	User getUserByNo(int no);
	User getUserByEmail(String email);
	User getUserByNickname(String nickname);
	boolean modifyProfile(int userNo, ProfileModifyRequestDto profileModifyRequestDto);
	boolean passwordcheck(int userNo,String password);
	boolean modifypassword(int userNo,String password);
	boolean deleteUser(User user);
	void loginfailed(int no);
	boolean logincheck(int no);
}

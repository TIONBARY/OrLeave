package com.orleave.service;

import java.util.List;

import com.orleave.dto.BanListDto;
import com.orleave.entity.User;

public interface BanService {
	void createBan(User user, int user2No);
	List<BanListDto> findByUserNo(int userNo);
	void deleteBan(User user, int user2No);
}

package com.orleave.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orleave.dto.UserListDto;

public interface ManagerService {
	Page<UserListDto> getUsers(Pageable pageable);
}

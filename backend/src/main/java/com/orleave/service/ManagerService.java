package com.orleave.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orleave.dto.ReportDetailDto;
import com.orleave.dto.UserListDto;
import com.orleave.dto.UserReportListDto;

public interface ManagerService {
	Page<UserListDto> getUsers(Pageable pageable);
	Page<UserReportListDto> getUserReports(Pageable pageable,int no);
	ReportDetailDto getReportDetail(int no);
	boolean BanUser(int no);
}

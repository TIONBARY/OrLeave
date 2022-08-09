package com.orleave.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orleave.dto.ReportDetailDto;
import com.orleave.dto.UserListDto;
import com.orleave.dto.UserReportListDto;
import com.orleave.dto.request.InquiryAnswerRequestDto;
import com.orleave.dto.request.NicknameModifyRequestDto;
import com.orleave.dto.request.NoticeModifyRequestDto;
import com.orleave.dto.request.NoticeRequestDto;

public interface ManagerService {
	Page<UserListDto> getUsers(Pageable pageable);
	Page<UserReportListDto> getUserReports(Pageable pageable,int no);
	ReportDetailDto getReportDetail(int no);
	void BanUser(int no);
	void ModifyNickname(NicknameModifyRequestDto nicknameModifyRequestDto);
	void InquiryAnswer(InquiryAnswerRequestDto inquiryAnswerRequestDto);
	void CreateNotices(NoticeRequestDto noticeRequestDto);
	void ModifyNotices(NoticeModifyRequestDto noticeModifyRequestDto);
	void DeleteNotices(int no);
}

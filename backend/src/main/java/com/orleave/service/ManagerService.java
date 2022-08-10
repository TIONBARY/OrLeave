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
	ReportDetailDto getReportDetail(int no)throws Exception;
	void BanUser(int no)throws Exception;
	void ModifyNickname(NicknameModifyRequestDto nicknameModifyRequestDto)throws Exception;
	void InquiryAnswer(InquiryAnswerRequestDto inquiryAnswerRequestDto)throws Exception;
	void CreateNotices(NoticeRequestDto noticeRequestDto)throws Exception;
	void ModifyNotices(NoticeModifyRequestDto noticeModifyRequestDto)throws Exception;
	void DeleteNotices(int no)throws Exception;
}

package com.orleave.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orleave.dto.NoticeDetailDto;
import com.orleave.dto.NoticeListDto;

public interface NoticeService {
	Page<NoticeListDto> getNotices(Pageable pageable);
	NoticeDetailDto getNoticeDetail(int no);
}

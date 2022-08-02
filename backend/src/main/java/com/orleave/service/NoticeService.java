package com.orleave.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.orleave.dto.NoticeDto;

public interface NoticeService {
	List<NoticeDto> getNotices(Pageable pageable);
}

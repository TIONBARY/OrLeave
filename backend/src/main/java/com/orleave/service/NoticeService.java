package com.orleave.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orleave.entity.Notice;

public interface NoticeService {
	Page<Notice> getNotices(Pageable pageable);
}

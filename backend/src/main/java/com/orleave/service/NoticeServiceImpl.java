package com.orleave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.orleave.entity.Notice;
import com.orleave.repository.NoticeRepository;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	NoticeRepository noticeRepository;

	@Override
	public Page<Notice> getNotices(Pageable pageable) {
		Page<Notice> notices = noticeRepository.findAll(pageable);
		return notices;
	}
}

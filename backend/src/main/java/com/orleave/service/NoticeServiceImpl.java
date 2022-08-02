package com.orleave.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orleave.dto.NoticeDto;
import com.orleave.entity.Notice;
import com.orleave.repository.NoticeRepository;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	NoticeRepository noticeRepository;

	@Override
	public List<NoticeDto> getNotices(Pageable pageable) {
		List<NoticeDto> noticeList = new ArrayList<>();
		Page<Notice> notices = noticeRepository.findAll(pageable);
		for (Notice notice : notices) {
			NoticeDto dto = NoticeDto.of(notice);
			noticeList.add(dto);
		}
		return noticeList;
	}
}

package com.orleave.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.orleave.dto.NoticeDetailDto;
import com.orleave.dto.NoticeListDto;
import com.orleave.entity.Notice;
import com.orleave.exception.NoticeNotFoundException;
import com.orleave.repository.NoticeRepository;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	NoticeRepository noticeRepository;

	@Override
	public Page<NoticeListDto> getNotices(Pageable pageable) throws Exception {
		Page<NoticeListDto> notices = noticeRepository.findAll(pageable).map(notice -> NoticeListDto.builder()
				.no(notice.getNo())
				.title(notice.getTitle())
				.createdTime(notice.getCreatedTime())
				.build());
		return notices;
	}

	@Override
	public NoticeDetailDto getNoticeDetail(int no) throws Exception {
		Optional<Notice> noticeTemp = noticeRepository.findById(no);
		if (!noticeTemp.isPresent()) throw new NoticeNotFoundException();
		Notice notice = noticeTemp.get();
		NoticeDetailDto dto = NoticeDetailDto.builder()
				.no(notice.getNo())
				.title(notice.getTitle())
				.content(notice.getContent())
				.createdTime(notice.getCreatedTime())
				.build();
		return dto;
	}
}

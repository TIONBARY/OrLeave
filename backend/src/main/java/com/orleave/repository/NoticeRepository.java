package com.orleave.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.orleave.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
	Page<Notice> findAll(Pageable pageable);
}

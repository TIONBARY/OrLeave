package com.orleave.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.orleave.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
	Page<Notice> findAll(Pageable pageable);
	Optional<Notice> findByNo(int no);
}

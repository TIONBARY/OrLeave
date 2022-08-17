package com.orleave.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
	Page<Notice> findAll(Pageable pageable);
	Optional<Notice> findByNo(int no);
}

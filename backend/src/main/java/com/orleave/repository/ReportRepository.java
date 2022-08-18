package com.orleave.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
	long countByUserNo(int no);
	Page<Report> findByReported(int no,Pageable pageable);
	long deleteByUserNo(int userNo);
}

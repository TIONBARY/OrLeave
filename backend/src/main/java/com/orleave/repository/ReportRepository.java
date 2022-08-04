package com.orleave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
	Long countByUser(int no);
}

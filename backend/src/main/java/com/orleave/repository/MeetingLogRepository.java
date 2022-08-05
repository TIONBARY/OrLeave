package com.orleave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.MeetingLog;

@Repository
public interface MeetingLogRepository extends JpaRepository<MeetingLog, Integer> {
	long countByUser1No(int no);
}

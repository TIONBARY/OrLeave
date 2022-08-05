package com.orleave.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.MeetingLog;

@Repository
public interface MeetingLogRepository extends JpaRepository<MeetingLog, Integer> {
	Long countByUser1(int no);
	List<MeetingLog> findByUser1NoAndCreatedTimeBetween(int userNo, LocalDateTime fromDateTime, LocalDateTime toDateTime);
}

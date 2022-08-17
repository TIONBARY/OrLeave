package com.orleave.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.MeetingLog;

@Repository
public interface MeetingLogRepository extends JpaRepository<MeetingLog, Integer> {
	Long countByUser1No(int no);
	List<MeetingLog> findByUser1NoAndCreatedTimeBetween(int userNo, LocalDateTime fromDateTime, LocalDateTime toDateTime);
	long deleteByUser1No(int user1No);
	long deleteByUser2No(int user2No);
}

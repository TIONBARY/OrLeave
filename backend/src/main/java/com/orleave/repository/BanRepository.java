package com.orleave.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.Ban;

@Repository
public interface BanRepository extends JpaRepository<Ban, Integer> {
	List<Ban> findByUserNo(int userNo);
	Optional<Ban> findByUserNoAndBannedNo(int userNo, int bannedNo);
	long deleteByUserNo(int userNo);
}

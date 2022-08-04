package com.orleave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orleave.entity.Ban;

public interface BanRepository extends JpaRepository<Ban, Integer> {
	List<Ban> findByUserNo(int userNo);
}

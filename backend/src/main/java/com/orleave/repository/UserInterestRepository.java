package com.orleave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.UserInterest;
import com.orleave.entity.UserInterestID;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, UserInterestID> {
	Long deleteByUserNo(int userNo);
}

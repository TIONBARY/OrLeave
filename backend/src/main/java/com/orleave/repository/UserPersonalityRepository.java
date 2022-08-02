package com.orleave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.UserPersonality;
import com.orleave.entity.UserPersonalityID;

@Repository
public interface UserPersonalityRepository extends JpaRepository<UserPersonality, UserPersonalityID> {
	Long deleteByUserNo(int userNo);
}

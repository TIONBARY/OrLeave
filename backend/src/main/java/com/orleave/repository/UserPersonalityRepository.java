package com.orleave.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orleave.entity.UserPersonality;
import com.orleave.entity.UserPersonalityID;

public interface UserPersonalityRepository extends JpaRepository<UserPersonality, UserPersonalityID> {

}

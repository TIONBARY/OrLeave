package com.orleave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.LoginTrial;

@Repository
public interface LoginTrialRepository extends JpaRepository<LoginTrial, Long> {

}

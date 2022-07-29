package com.orleave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.EmailConfirm;

@Repository
public interface EmailConfirmRepository extends JpaRepository<EmailConfirm, String> {

}

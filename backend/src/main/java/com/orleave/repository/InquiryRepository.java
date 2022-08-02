package com.orleave.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.Inquiry;
import com.orleave.entity.User;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {
	Page<Inquiry> findByUser(User user, Pageable pageable);
}

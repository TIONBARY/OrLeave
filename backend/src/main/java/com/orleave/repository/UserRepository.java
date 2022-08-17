package com.orleave.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orleave.entity.Notice;
import com.orleave.entity.User;

import java.util.Optional;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Page<User> findAll(Pageable pageable);
}
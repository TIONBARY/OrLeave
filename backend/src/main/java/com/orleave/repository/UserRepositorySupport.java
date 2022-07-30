package com.orleave.repository;

import com.orleave.entity.QUser;
import com.orleave.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;

    public Optional<User> findUserByEmail(String email) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.email.eq(email)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }
    
    public Optional<User> findUserByNickname(String nickname) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.nickname.eq(nickname)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }
}

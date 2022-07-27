package com.orleave.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1425234193L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> birthDay = createNumber("birthDay", Integer.class);

    public final NumberPath<Integer> birthMonth = createNumber("birthMonth", Integer.class);

    public final NumberPath<Integer> birthYear = createNumber("birthYear", Integer.class);

    public final NumberPath<Integer> drink = createNumber("drink", Integer.class);

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final StringPath mbti = createString("mbti");

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Long> no = createNumber("no", Long.class);

    public final StringPath password = createString("password");

    public final NumberPath<Integer> religion = createNumber("religion", Integer.class);

    public final NumberPath<Integer> smoke = createNumber("smoke", Integer.class);

    public final StringPath userType = createString("userType");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}


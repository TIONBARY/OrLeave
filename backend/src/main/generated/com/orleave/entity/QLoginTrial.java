package com.orleave.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLoginTrial is a Querydsl query type for LoginTrial
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLoginTrial extends EntityPathBase<LoginTrial> {

    private static final long serialVersionUID = -1409861295L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLoginTrial loginTrial = new QLoginTrial("loginTrial");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> time = createDateTime("time", java.time.LocalDateTime.class);

    public final QUser user;

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public QLoginTrial(String variable) {
        this(LoginTrial.class, forVariable(variable), INITS);
    }

    public QLoginTrial(Path<? extends LoginTrial> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLoginTrial(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLoginTrial(PathMetadata metadata, PathInits inits) {
        this(LoginTrial.class, metadata, inits);
    }

    public QLoginTrial(Class<? extends LoginTrial> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}


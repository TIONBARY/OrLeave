package com.orleave.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBan is a Querydsl query type for Ban
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBan extends EntityPathBase<Ban> {

    private static final long serialVersionUID = -877278101L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBan ban = new QBan("ban");

    public final NumberPath<Integer> bannedNo = createNumber("bannedNo", Integer.class);

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final QUser user;

    public QBan(String variable) {
        this(Ban.class, forVariable(variable), INITS);
    }

    public QBan(Path<? extends Ban> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBan(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBan(PathMetadata metadata, PathInits inits) {
        this(Ban.class, metadata, inits);
    }

    public QBan(Class<? extends Ban> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}


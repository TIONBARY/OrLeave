package com.orleave.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserPersonality is a Querydsl query type for UserPersonality
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserPersonality extends EntityPathBase<UserPersonality> {

    private static final long serialVersionUID = -542281729L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserPersonality userPersonality = new QUserPersonality("userPersonality");

    public final NumberPath<Integer> personality = createNumber("personality", Integer.class);

    public final QUser user;

    public QUserPersonality(String variable) {
        this(UserPersonality.class, forVariable(variable), INITS);
    }

    public QUserPersonality(Path<? extends UserPersonality> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserPersonality(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserPersonality(PathMetadata metadata, PathInits inits) {
        this(UserPersonality.class, metadata, inits);
    }

    public QUserPersonality(Class<? extends UserPersonality> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}


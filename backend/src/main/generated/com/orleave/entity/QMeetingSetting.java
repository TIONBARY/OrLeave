package com.orleave.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMeetingSetting is a Querydsl query type for MeetingSetting
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMeetingSetting extends EntityPathBase<MeetingSetting> {

    private static final long serialVersionUID = 903909817L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMeetingSetting meetingSetting = new QMeetingSetting("meetingSetting");

    public final NumberPath<Integer> ageMax = createNumber("ageMax", Integer.class);

    public final NumberPath<Integer> ageMin = createNumber("ageMin", Integer.class);

    public final NumberPath<Integer> distance = createNumber("distance", Integer.class);

    public final NumberPath<Integer> drinkMax = createNumber("drinkMax", Integer.class);

    public final NumberPath<Integer> drinkMin = createNumber("drinkMin", Integer.class);

    public final NumberPath<Integer> religion = createNumber("religion", Integer.class);

    public final NumberPath<Integer> smoke = createNumber("smoke", Integer.class);

    public final QUser user;

    public final NumberPath<Integer> userNo = createNumber("userNo", Integer.class);

    public QMeetingSetting(String variable) {
        this(MeetingSetting.class, forVariable(variable), INITS);
    }

    public QMeetingSetting(Path<? extends MeetingSetting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMeetingSetting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMeetingSetting(PathMetadata metadata, PathInits inits) {
        this(MeetingSetting.class, metadata, inits);
    }

    public QMeetingSetting(Class<? extends MeetingSetting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}


package com.orleave.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMeetingLog is a Querydsl query type for MeetingLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMeetingLog extends EntityPathBase<MeetingLog> {

    private static final long serialVersionUID = 1150763821L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMeetingLog meetingLog = new QMeetingLog("meetingLog");

    public final DateTimePath<java.time.LocalDateTime> createdTime = createDateTime("createdTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final QUser user1;

    public final NumberPath<Integer> user2 = createNumber("user2", Integer.class);

    public QMeetingLog(String variable) {
        this(MeetingLog.class, forVariable(variable), INITS);
    }

    public QMeetingLog(Path<? extends MeetingLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMeetingLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMeetingLog(PathMetadata metadata, PathInits inits) {
        this(MeetingLog.class, metadata, inits);
    }

    public QMeetingLog(Class<? extends MeetingLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user1 = inits.isInitialized("user1") ? new QUser(forProperty("user1"), inits.get("user1")) : null;
    }

}


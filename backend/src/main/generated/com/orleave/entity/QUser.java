package com.orleave.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1425234193L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final ListPath<Ban, QBan> bans = this.<Ban, QBan>createList("bans", Ban.class, QBan.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> birthDay = createDate("birthDay", java.time.LocalDate.class);

    public final NumberPath<Integer> drink = createNumber("drink", Integer.class);

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final NumberPath<Integer> imageNo = createNumber("imageNo", Integer.class);

    public final ListPath<Inquiry, QInquiry> inquiries = this.<Inquiry, QInquiry>createList("inquiries", Inquiry.class, QInquiry.class, PathInits.DIRECT2);

    public final ListPath<UserInterest, QUserInterest> interests = this.<UserInterest, QUserInterest>createList("interests", UserInterest.class, QUserInterest.class, PathInits.DIRECT2);

    public final StringPath mbti = createString("mbti");

    public final ListPath<MeetingLog, QMeetingLog> meetinglogs = this.<MeetingLog, QMeetingLog>createList("meetinglogs", MeetingLog.class, QMeetingLog.class, PathInits.DIRECT2);

    public final QMeetingSetting meetingSetting;

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath password = createString("password");

    public final ListPath<UserPersonality, QUserPersonality> personalities = this.<UserPersonality, QUserPersonality>createList("personalities", UserPersonality.class, QUserPersonality.class, PathInits.DIRECT2);

    public final NumberPath<Integer> religion = createNumber("religion", Integer.class);

    public final ListPath<Report, QReport> reports = this.<Report, QReport>createList("reports", Report.class, QReport.class, PathInits.DIRECT2);

    public final NumberPath<Integer> smoke = createNumber("smoke", Integer.class);

    public final StringPath userType = createString("userType");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.meetingSetting = inits.isInitialized("meetingSetting") ? new QMeetingSetting(forProperty("meetingSetting"), inits.get("meetingSetting")) : null;
    }

}


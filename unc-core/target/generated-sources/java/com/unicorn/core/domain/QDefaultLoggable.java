package com.unicorn.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDefaultLoggable is a Querydsl query type for DefaultLoggable
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QDefaultLoggable extends EntityPathBase<DefaultLoggable> {

    private static final long serialVersionUID = -866417024L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDefaultLoggable defaultLoggable = new QDefaultLoggable("defaultLoggable");

    public final QDefaultIdentifiable _super = new QDefaultIdentifiable(this);

    public final com.unicorn.system.domain.po.QUser createdBy;

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.util.Date> lastUpdateDate = createDateTime("lastUpdateDate", java.util.Date.class);

    public final com.unicorn.system.domain.po.QUser lastUpdatedBy;

    //inherited
    public final StringPath objectId = _super.objectId;

    public QDefaultLoggable(String variable) {
        this(DefaultLoggable.class, forVariable(variable), INITS);
    }

    public QDefaultLoggable(Path<? extends DefaultLoggable> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDefaultLoggable(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDefaultLoggable(PathMetadata metadata, PathInits inits) {
        this(DefaultLoggable.class, metadata, inits);
    }

    public QDefaultLoggable(Class<? extends DefaultLoggable> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.createdBy = inits.isInitialized("createdBy") ? new com.unicorn.system.domain.po.QUser(forProperty("createdBy"), inits.get("createdBy")) : null;
        this.lastUpdatedBy = inits.isInitialized("lastUpdatedBy") ? new com.unicorn.system.domain.po.QUser(forProperty("lastUpdatedBy"), inits.get("lastUpdatedBy")) : null;
    }

}


package com.unicorn.core.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QDefaultLoggable is a Querydsl query type for DefaultLoggable
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QDefaultLoggable extends EntityPathBase<DefaultLoggable> {

    private static final long serialVersionUID = -866417024L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDefaultLoggable defaultLoggable = new QDefaultLoggable("defaultLoggable");

    public final QDefaultIdentifiable _super = new QDefaultIdentifiable(this);

    public final com.unicorn.system.domain.po.QUser createdBy;

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> lastUpdateDate = createDateTime("lastUpdateDate", java.util.Date.class);

    public final com.unicorn.system.domain.po.QUser lastUpdatedBy;

    //inherited
    public final StringPath objectId = _super.objectId;

    public QDefaultLoggable(String variable) {
        this(DefaultLoggable.class, forVariable(variable), INITS);
    }

    public QDefaultLoggable(Path<? extends DefaultLoggable> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDefaultLoggable(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDefaultLoggable(PathMetadata<?> metadata, PathInits inits) {
        this(DefaultLoggable.class, metadata, inits);
    }

    public QDefaultLoggable(Class<? extends DefaultLoggable> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.createdBy = inits.isInitialized("createdBy") ? new com.unicorn.system.domain.po.QUser(forProperty("createdBy"), inits.get("createdBy")) : null;
        this.lastUpdatedBy = inits.isInitialized("lastUpdatedBy") ? new com.unicorn.system.domain.po.QUser(forProperty("lastUpdatedBy"), inits.get("lastUpdatedBy")) : null;
    }

}


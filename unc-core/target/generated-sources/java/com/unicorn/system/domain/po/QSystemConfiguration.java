package com.unicorn.system.domain.po;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QSystemConfiguration is a Querydsl query type for Configuration
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QSystemConfiguration extends EntityPathBase<Configuration> {

    private static final long serialVersionUID = 2034529350L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSystemConfiguration systemConfiguration = new QSystemConfiguration("systemConfiguration");

    public final com.unicorn.core.domain.QDefaultLoggable _super;

    // inherited
    public final QUser createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdDate;

    public final StringPath key = createString("key");

    //inherited
    public final DateTimePath<java.util.Date> lastUpdateDate;

    // inherited
    public final QUser lastUpdatedBy;

    //inherited
    public final StringPath objectId;

    public final StringPath value = createString("value");

    public QSystemConfiguration(String variable) {
        this(Configuration.class, forVariable(variable), INITS);
    }

    public QSystemConfiguration(Path<? extends Configuration> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSystemConfiguration(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSystemConfiguration(PathMetadata<?> metadata, PathInits inits) {
        this(Configuration.class, metadata, inits);
    }

    public QSystemConfiguration(Class<? extends Configuration> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.unicorn.core.domain.QDefaultLoggable(type, metadata, inits);
        this.createdBy = _super.createdBy;
        this.createdDate = _super.createdDate;
        this.lastUpdateDate = _super.lastUpdateDate;
        this.lastUpdatedBy = _super.lastUpdatedBy;
        this.objectId = _super.objectId;
    }

}


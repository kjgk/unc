package com.unicorn.core.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QDefaultPersistent is a Querydsl query type for DefaultPersistent
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QDefaultPersistent extends EntityPathBase<DefaultPersistent> {

    private static final long serialVersionUID = 706678490L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDefaultPersistent defaultPersistent = new QDefaultPersistent("defaultPersistent");

    public final QDefaultLoggable _super;

    // inherited
    public final com.unicorn.system.domain.po.QUser createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdDate;

    public final NumberPath<Integer> deleted = createNumber("deleted", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> lastUpdateDate;

    // inherited
    public final com.unicorn.system.domain.po.QUser lastUpdatedBy;

    //inherited
    public final StringPath objectId;

    public QDefaultPersistent(String variable) {
        this(DefaultPersistent.class, forVariable(variable), INITS);
    }

    public QDefaultPersistent(Path<? extends DefaultPersistent> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDefaultPersistent(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDefaultPersistent(PathMetadata<?> metadata, PathInits inits) {
        this(DefaultPersistent.class, metadata, inits);
    }

    public QDefaultPersistent(Class<? extends DefaultPersistent> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDefaultLoggable(type, metadata, inits);
        this.createdBy = _super.createdBy;
        this.createdDate = _super.createdDate;
        this.lastUpdateDate = _super.lastUpdateDate;
        this.lastUpdatedBy = _super.lastUpdatedBy;
        this.objectId = _super.objectId;
    }

}


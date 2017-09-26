package com.unicorn.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDefaultPersistent is a Querydsl query type for DefaultPersistent
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
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
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDefaultPersistent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDefaultPersistent(PathMetadata metadata, PathInits inits) {
        this(DefaultPersistent.class, metadata, inits);
    }

    public QDefaultPersistent(Class<? extends DefaultPersistent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDefaultLoggable(type, metadata, inits);
        this.createdBy = _super.createdBy;
        this.createdDate = _super.createdDate;
        this.lastUpdateDate = _super.lastUpdateDate;
        this.lastUpdatedBy = _super.lastUpdatedBy;
        this.objectId = _super.objectId;
    }

}


package com.unicorn.system.domain.po;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QAuthority is a Querydsl query type for Authority
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAuthority extends EntityPathBase<Authority> {

    private static final long serialVersionUID = -2034124990L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuthority authority = new QAuthority("authority");

    public final com.unicorn.core.domain.QDefaultNomenclator _super;

    // inherited
    public final QUser createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdDate;

    //inherited
    public final NumberPath<Integer> deleted;

    //inherited
    public final StringPath description;

    //inherited
    public final DateTimePath<java.util.Date> lastUpdateDate;

    // inherited
    public final QUser lastUpdatedBy;

    //inherited
    public final StringPath name;

    //inherited
    public final StringPath objectId;

    public final StringPath tag = createString("tag");

    public QAuthority(String variable) {
        this(Authority.class, forVariable(variable), INITS);
    }

    public QAuthority(Path<? extends Authority> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAuthority(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAuthority(PathMetadata<?> metadata, PathInits inits) {
        this(Authority.class, metadata, inits);
    }

    public QAuthority(Class<? extends Authority> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.unicorn.core.domain.QDefaultNomenclator(type, metadata, inits);
        this.createdBy = _super.createdBy;
        this.createdDate = _super.createdDate;
        this.deleted = _super.deleted;
        this.description = _super.description;
        this.lastUpdateDate = _super.lastUpdateDate;
        this.lastUpdatedBy = _super.lastUpdatedBy;
        this.name = _super.name;
        this.objectId = _super.objectId;
    }

}


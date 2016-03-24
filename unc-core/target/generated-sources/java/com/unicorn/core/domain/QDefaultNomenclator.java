package com.unicorn.core.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QDefaultNomenclator is a Querydsl query type for DefaultNomenclator
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QDefaultNomenclator extends EntityPathBase<DefaultNomenclator> {

    private static final long serialVersionUID = -739223023L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDefaultNomenclator defaultNomenclator = new QDefaultNomenclator("defaultNomenclator");

    public final QDefaultPersistent _super;

    // inherited
    public final com.unicorn.system.domain.po.QUser createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdDate;

    //inherited
    public final NumberPath<Integer> deleted;

    public final StringPath description = createString("description");

    //inherited
    public final DateTimePath<java.util.Date> lastUpdateDate;

    // inherited
    public final com.unicorn.system.domain.po.QUser lastUpdatedBy;

    public final StringPath name = createString("name");

    //inherited
    public final StringPath objectId;

    public QDefaultNomenclator(String variable) {
        this(DefaultNomenclator.class, forVariable(variable), INITS);
    }

    public QDefaultNomenclator(Path<? extends DefaultNomenclator> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDefaultNomenclator(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDefaultNomenclator(PathMetadata<?> metadata, PathInits inits) {
        this(DefaultNomenclator.class, metadata, inits);
    }

    public QDefaultNomenclator(Class<? extends DefaultNomenclator> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDefaultPersistent(type, metadata, inits);
        this.createdBy = _super.createdBy;
        this.createdDate = _super.createdDate;
        this.deleted = _super.deleted;
        this.lastUpdateDate = _super.lastUpdateDate;
        this.lastUpdatedBy = _super.lastUpdatedBy;
        this.objectId = _super.objectId;
    }

}


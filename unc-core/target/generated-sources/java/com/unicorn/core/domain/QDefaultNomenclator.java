package com.unicorn.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDefaultNomenclator is a Querydsl query type for DefaultNomenclator
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
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
    public final NumberPath<Long> objectId;

    public QDefaultNomenclator(String variable) {
        this(DefaultNomenclator.class, forVariable(variable), INITS);
    }

    public QDefaultNomenclator(Path<? extends DefaultNomenclator> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDefaultNomenclator(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDefaultNomenclator(PathMetadata metadata, PathInits inits) {
        this(DefaultNomenclator.class, metadata, inits);
    }

    public QDefaultNomenclator(Class<? extends DefaultNomenclator> type, PathMetadata metadata, PathInits inits) {
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


package com.unicorn.system.domain.po;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QCode is a Querydsl query type for Code
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QCode extends EntityPathBase<Code> {

    private static final long serialVersionUID = 1409596046L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCode code = new QCode("code");

    public final com.unicorn.core.domain.QDefaultRecursive _super;

    public final ListPath<Code, QCode> childList = this.<Code, QCode>createList("childList", Code.class, QCode.class, PathInits.DIRECT2);

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

    //inherited
    public final NumberPath<Integer> orderNo;

    public final QCode parent;

    public final StringPath tag = createString("tag");

    public QCode(String variable) {
        this(Code.class, forVariable(variable), INITS);
    }

    public QCode(Path<? extends Code> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QCode(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QCode(PathMetadata<?> metadata, PathInits inits) {
        this(Code.class, metadata, inits);
    }

    public QCode(Class<? extends Code> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.unicorn.core.domain.QDefaultRecursive(type, metadata, inits);
        this.createdBy = _super.createdBy;
        this.createdDate = _super.createdDate;
        this.deleted = _super.deleted;
        this.description = _super.description;
        this.lastUpdateDate = _super.lastUpdateDate;
        this.lastUpdatedBy = _super.lastUpdatedBy;
        this.name = _super.name;
        this.objectId = _super.objectId;
        this.orderNo = _super.orderNo;
        this.parent = inits.isInitialized("parent") ? new QCode(forProperty("parent"), inits.get("parent")) : null;
    }

}


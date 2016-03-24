package com.unicorn.core.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QDefaultRecursive is a Querydsl query type for DefaultRecursive
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QDefaultRecursive extends EntityPathBase<DefaultRecursive<?>> {

    private static final long serialVersionUID = 1864781711L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDefaultRecursive defaultRecursive = new QDefaultRecursive("defaultRecursive");

    public final QDefaultNomenclator _super;

    public final ListPath<Object, SimplePath<Object>> childList = this.<Object, SimplePath<Object>>createList("childList", Object.class, SimplePath.class, PathInits.DIRECT2);

    // inherited
    public final com.unicorn.system.domain.po.QUser createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdDate;

    //inherited
    public final NumberPath<Integer> deleted;

    //inherited
    public final StringPath description;

    //inherited
    public final DateTimePath<java.util.Date> lastUpdateDate;

    // inherited
    public final com.unicorn.system.domain.po.QUser lastUpdatedBy;

    //inherited
    public final StringPath name;

    //inherited
    public final StringPath objectId;

    public final NumberPath<Integer> orderNo = createNumber("orderNo", Integer.class);

    public final SimplePath<Object> parent = createSimple("parent", Object.class);

    @SuppressWarnings("all")
    public QDefaultRecursive(String variable) {
        this((Class)DefaultRecursive.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QDefaultRecursive(Path<? extends DefaultRecursive> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDefaultRecursive(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    @SuppressWarnings("all")
    public QDefaultRecursive(PathMetadata<?> metadata, PathInits inits) {
        this((Class)DefaultRecursive.class, metadata, inits);
    }

    public QDefaultRecursive(Class<? extends DefaultRecursive<?>> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDefaultNomenclator(type, metadata, inits);
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


package com.unicorn.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDefaultRecursive is a Querydsl query type for DefaultRecursive
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QDefaultRecursive extends EntityPathBase<DefaultRecursive<?>> {

    private static final long serialVersionUID = 1864781711L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDefaultRecursive defaultRecursive = new QDefaultRecursive("defaultRecursive");

    public final QDefaultNomenclator _super;

    public final ListPath<DefaultRecursive<?>, QDefaultRecursive> childList = this.<DefaultRecursive<?>, QDefaultRecursive>createList("childList", DefaultRecursive.class, QDefaultRecursive.class, PathInits.DIRECT2);

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

    public final QDefaultRecursive parent;

    public final StringPath parentId = createString("parentId");

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QDefaultRecursive(String variable) {
        this((Class) DefaultRecursive.class, forVariable(variable), INITS);
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QDefaultRecursive(Path<? extends DefaultRecursive> path) {
        this((Class) path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDefaultRecursive(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QDefaultRecursive(PathMetadata metadata, PathInits inits) {
        this((Class) DefaultRecursive.class, metadata, inits);
    }

    public QDefaultRecursive(Class<? extends DefaultRecursive<?>> type, PathMetadata metadata, PathInits inits) {
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
        this.parent = inits.isInitialized("parent") ? new QDefaultRecursive(forProperty("parent"), inits.get("parent")) : null;
    }

}


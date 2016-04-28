package com.unicorn.core.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QDefaultRecursivePath is a Querydsl query type for DefaultRecursivePath
 */
@Generated("com.mysema.query.codegen.SupertypeSerializer")
public class QDefaultRecursivePath extends EntityPathBase<DefaultRecursivePath<?>> {

    private static final long serialVersionUID = -848574380L;

    public static final QDefaultRecursivePath defaultRecursivePath = new QDefaultRecursivePath("defaultRecursivePath");

    public final QDefaultIdentifiable _super = new QDefaultIdentifiable(this);

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final SimplePath<Object> genuine = createSimple("genuine", Object.class);

    //inherited
    public final StringPath objectId = _super.objectId;

    public final StringPath path = createString("path");

    @SuppressWarnings("all")
    public QDefaultRecursivePath(String variable) {
        super((Class)DefaultRecursivePath.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QDefaultRecursivePath(Path<? extends DefaultRecursivePath> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    @SuppressWarnings("all")
    public QDefaultRecursivePath(PathMetadata<?> metadata) {
        super((Class)DefaultRecursivePath.class, metadata);
    }

}


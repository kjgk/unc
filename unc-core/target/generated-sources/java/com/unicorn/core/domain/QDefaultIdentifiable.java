package com.unicorn.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDefaultIdentifiable is a Querydsl query type for DefaultIdentifiable
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QDefaultIdentifiable extends EntityPathBase<DefaultIdentifiable> {

    private static final long serialVersionUID = 1422041561L;

    public static final QDefaultIdentifiable defaultIdentifiable = new QDefaultIdentifiable("defaultIdentifiable");

    public final StringPath objectId = createString("objectId");

    public QDefaultIdentifiable(String variable) {
        super(DefaultIdentifiable.class, forVariable(variable));
    }

    public QDefaultIdentifiable(Path<? extends DefaultIdentifiable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDefaultIdentifiable(PathMetadata metadata) {
        super(DefaultIdentifiable.class, metadata);
    }

}


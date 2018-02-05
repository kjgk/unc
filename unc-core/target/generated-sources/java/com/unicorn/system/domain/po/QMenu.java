package com.unicorn.system.domain.po;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenu is a Querydsl query type for Menu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMenu extends EntityPathBase<Menu> {

    private static final long serialVersionUID = 1409884672L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenu menu = new QMenu("menu");

    public final com.unicorn.core.domain.QDefaultRecursive _super;

    public final ListPath<Menu, QMenu> childList = this.<Menu, QMenu>createList("childList", Menu.class, QMenu.class, PathInits.DIRECT2);

    // inherited
    public final QUser createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdDate;

    //inherited
    public final NumberPath<Integer> deleted;

    //inherited
    public final StringPath description;

    public final NumberPath<Integer> enabled = createNumber("enabled", Integer.class);

    public final NumberPath<Integer> hidden = createNumber("hidden", Integer.class);

    public final StringPath icon = createString("icon");

    //inherited
    public final NumberPath<Long> id;

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

    public final QMenu parent;

    public final StringPath tag = createString("tag");

    public final StringPath url = createString("url");

    public QMenu(String variable) {
        this(Menu.class, forVariable(variable), INITS);
    }

    public QMenu(Path<? extends Menu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenu(PathMetadata metadata, PathInits inits) {
        this(Menu.class, metadata, inits);
    }

    public QMenu(Class<? extends Menu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.unicorn.core.domain.QDefaultRecursive(type, metadata, inits);
        this.createdBy = _super.createdBy;
        this.createdDate = _super.createdDate;
        this.deleted = _super.deleted;
        this.description = _super.description;
        this.id = _super.id;
        this.lastUpdateDate = _super.lastUpdateDate;
        this.lastUpdatedBy = _super.lastUpdatedBy;
        this.name = _super.name;
        this.objectId = _super.objectId;
        this.orderNo = _super.orderNo;
        this.parent = inits.isInitialized("parent") ? new QMenu(forProperty("parent"), inits.get("parent")) : null;
    }

}


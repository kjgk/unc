package com.unicorn.system.domain.po;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoleMenu is a Querydsl query type for RoleMenu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoleMenu extends EntityPathBase<RoleMenu> {

    private static final long serialVersionUID = 451261206L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoleMenu roleMenu = new QRoleMenu("roleMenu");

    public final com.unicorn.core.domain.QDefaultIdentifiable _super = new com.unicorn.core.domain.QDefaultIdentifiable(this);

    public final QMenu menu;

    //inherited
    public final NumberPath<Long> objectId = _super.objectId;

    public final QRole role;

    public QRoleMenu(String variable) {
        this(RoleMenu.class, forVariable(variable), INITS);
    }

    public QRoleMenu(Path<? extends RoleMenu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRoleMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRoleMenu(PathMetadata metadata, PathInits inits) {
        this(RoleMenu.class, metadata, inits);
    }

    public QRoleMenu(Class<? extends RoleMenu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menu = inits.isInitialized("menu") ? new QMenu(forProperty("menu"), inits.get("menu")) : null;
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role"), inits.get("role")) : null;
    }

}


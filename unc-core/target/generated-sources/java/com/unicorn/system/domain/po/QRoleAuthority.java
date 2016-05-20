package com.unicorn.system.domain.po;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QRoleAuthority is a Querydsl query type for RoleAuthority
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRoleAuthority extends EntityPathBase<RoleAuthority> {

    private static final long serialVersionUID = 1034052588L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRoleAuthority roleAuthority = new QRoleAuthority("roleAuthority");

    public final com.unicorn.core.domain.QDefaultIdentifiable _super = new com.unicorn.core.domain.QDefaultIdentifiable(this);

    public final QAuthority authority;

    //inherited
    public final StringPath objectId = _super.objectId;

    public final QRole role;

    public QRoleAuthority(String variable) {
        this(RoleAuthority.class, forVariable(variable), INITS);
    }

    public QRoleAuthority(Path<? extends RoleAuthority> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRoleAuthority(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRoleAuthority(PathMetadata<?> metadata, PathInits inits) {
        this(RoleAuthority.class, metadata, inits);
    }

    public QRoleAuthority(Class<? extends RoleAuthority> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.authority = inits.isInitialized("authority") ? new QAuthority(forProperty("authority"), inits.get("authority")) : null;
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role"), inits.get("role")) : null;
    }

}


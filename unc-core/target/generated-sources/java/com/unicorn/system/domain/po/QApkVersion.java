package com.unicorn.system.domain.po;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QApkVersion is a Querydsl query type for ApkVersion
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QApkVersion extends EntityPathBase<ApkVersion> {

    private static final long serialVersionUID = -67620099L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApkVersion apkVersion = new QApkVersion("apkVersion");

    public final com.unicorn.core.domain.QDefaultLoggable _super;

    public final StringPath apkId = createString("apkId");

    public final StringPath code = createString("code");

    // inherited
    public final QUser createdBy;

    //inherited
    public final DateTimePath<java.util.Date> createdDate;

    public final StringPath filename = createString("filename");

    public final NumberPath<Integer> fileSize = createNumber("fileSize", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> lastUpdateDate;

    // inherited
    public final QUser lastUpdatedBy;

    //inherited
    public final StringPath objectId;

    public final StringPath version = createString("version");

    public QApkVersion(String variable) {
        this(ApkVersion.class, forVariable(variable), INITS);
    }

    public QApkVersion(Path<? extends ApkVersion> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QApkVersion(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QApkVersion(PathMetadata<?> metadata, PathInits inits) {
        this(ApkVersion.class, metadata, inits);
    }

    public QApkVersion(Class<? extends ApkVersion> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.unicorn.core.domain.QDefaultLoggable(type, metadata, inits);
        this.createdBy = _super.createdBy;
        this.createdDate = _super.createdDate;
        this.lastUpdateDate = _super.lastUpdateDate;
        this.lastUpdatedBy = _super.lastUpdatedBy;
        this.objectId = _super.objectId;
    }

}


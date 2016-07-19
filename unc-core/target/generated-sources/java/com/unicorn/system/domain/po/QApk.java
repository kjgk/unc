package com.unicorn.system.domain.po;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QApk is a Querydsl query type for Apk
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QApk extends EntityPathBase<Apk> {

    private static final long serialVersionUID = -2032741029L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApk apk = new QApk("apk");

    public final com.unicorn.core.domain.QDefaultNomenclator _super;

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

    public final StringPath tag = createString("tag");

    public final ListPath<ApkVersion, QApkVersion> versionList = this.<ApkVersion, QApkVersion>createList("versionList", ApkVersion.class, QApkVersion.class, PathInits.DIRECT2);

    public QApk(String variable) {
        this(Apk.class, forVariable(variable), INITS);
    }

    public QApk(Path<? extends Apk> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QApk(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QApk(PathMetadata<?> metadata, PathInits inits) {
        this(Apk.class, metadata, inits);
    }

    public QApk(Class<? extends Apk> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.unicorn.core.domain.QDefaultNomenclator(type, metadata, inits);
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


package com.unicorn.core.repository;

import com.unicorn.core.domain.Identifiable;
import com.unicorn.core.domain.Persistent;
import com.unicorn.core.query.QueryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

import javax.persistence.EntityManager;

public class BaseRepositoryImpl<T extends Identifiable> extends QueryDslJpaRepository<T, String> implements BaseRepository<T> {

    public final static String DELETED_FIELD = "deleted";

    private final EntityManager entityManager;


    public BaseRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {

        super(entityInformation, entityManager);

        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {

        return this.entityManager;
    }

    public void logicDelete(String objectId) {

        T entity = findOne(objectId);
        if (null == entity || !(entity instanceof Persistent)) {
            return;
        }
        Persistent model = (Persistent) entity;
        model.setDeleted(1);
        this.entityManager.merge(model);
    }

    public void logicDelete(T entity) {

        this.logicDelete(entity.getObjectId());
    }

    public Page<T> findAll(QueryInfo queryInfo) {

        Pageable pageable = new PageRequest(queryInfo.getPageInfo().getPageNo() - 1, queryInfo.getPageInfo().getPageSize(), queryInfo.getSort());
        return this.findAll(queryInfo.getPredicate(), pageable);
    }
}

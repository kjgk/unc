package com.unicorn.core.repository;

import com.unicorn.core.query.QueryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T> extends CrudRepository<T, String>, QueryDslPredicateExecutor<T> {

    EntityManager getEntityManager();

    /**
     * 逻辑删除
     *
     * @param objectId
     */
    void logicDelete(String objectId);

    void logicDelete(T entity);

    /**
     * 基于QueryDSL的翻页查询
     *
     * @param queryInfo
     * @return
     */
    Page<T> findAll(QueryInfo queryInfo);

    List<T> findAll();

    T findRoot();

    T findRoot(String tag);
}

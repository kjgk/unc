package com.unicorn.core.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.unicorn.core.query.QueryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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

    /**
     * 逻辑删除
     *
     * @param entity
     */
    void logicDelete(T entity);

    /**
     * 基于QueryDSL的翻页查询
     *
     * @param queryInfo
     * @return
     */
    Page<T> findAll(QueryInfo queryInfo);

    /**
     * 查询所有记录，不包含删除的记录
     * @return
     */
    List<T> findAll();

    /**
     * 基于QueryDSL的查询
     *
     * @param predicate
     * @return
     */
    List<T> findAll(Predicate predicate);

    /**
     * 基于QueryDSL的查询
     *
     * @param predicate
     * @return
     */
    public List<T> findAll(Predicate predicate, Sort sort);

    /**
     * 基于QueryDSL的查询
     *
     * @param predicate
     * @return
     */
    Iterable<T> findAll(Predicate predicate, OrderSpecifier... orderSpecifiers);

    long count(Predicate predicate);

    T findRoot();

    T findRoot(String tag);
}

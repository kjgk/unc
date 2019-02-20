package com.unicorn.common.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.unicorn.common.domain.vo.BasicInfo;
import com.unicorn.common.query.QueryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long>, QuerydslPredicateExecutor<T> {

    EntityManager getEntityManager();

    <S extends T> S get(Long objectId);

    <S extends T> S get(Predicate predicate);

    /**
     * 逻辑删除
     */
    void logicDelete(Long objectId);

    /**
     * 逻辑删除
     */
    void logicDelete(T entity);

    /**
     * 基于QueryDSL的翻页查询
     */
    Page<T> findAll(QueryInfo queryInfo);

    /**
     * 查询所有记录，不包含删除的记录
     */
    List<T> findAll();

    /**
     * 基于QueryDSL的查询
     */
    List<T> findAll(Predicate predicate);

    /**
     * 基于QueryDSL的查询
     */
    List<T> findAll(Predicate predicate, Sort sort);

    /**
     * 基于QueryDSL的查询
     */
    Iterable<T> findAll(Predicate predicate, OrderSpecifier... orderSpecifiers);

    long count(Predicate predicate);

    List<BasicInfo> list();

    T findRoot();

    T findRoot(String tag);

    Map move(Long objectId, Long targetId, Integer position);
}

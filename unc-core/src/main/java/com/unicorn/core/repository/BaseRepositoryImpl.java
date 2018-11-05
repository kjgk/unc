package com.unicorn.core.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.unicorn.core.domain.DefaultPersistent;
import com.unicorn.core.domain.Identifiable;
import com.unicorn.core.domain.Persistent;
import com.unicorn.core.domain.vo.BasicInfo;
import com.unicorn.core.exception.ServiceException;
import com.unicorn.core.query.QueryInfo;
import com.unicorn.utils.Identities;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class BaseRepositoryImpl<T extends Identifiable> extends QuerydslJpaRepository<T, String> implements BaseRepository<T> {

    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

    private final EntityManager entityManager;

    private final JpaEntityInformation entityInformation;


    public BaseRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {

        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    public EntityManager getEntityManager() {

        return this.entityManager;
    }

    public <S extends T> S get(String objectId) {

        return (S) findById(objectId).orElse(null);
    }

    public <S extends T> S get(Predicate predicate) {

        Pageable pageable = PageRequest.of(0, 1);
        Predicate expression = pretreatmentPredicate(predicate);
        Page<T> page = findAll(expression, pageable);
        if (page.getSize() == 0) {
            return null;
        } else if (page.getSize() == 1) {
            return (S) page.getContent().get(0);
        } else {
            throw new ServiceException("More than one row with the given predicate");
        }
    }

    public <S extends T> S save(S entity) {

        if (StringUtils.isEmpty(entity.getObjectId())) {
            entity.setObjectId(Identities.uuid2());
            return super.save(entity);
        }

        if (!(entity instanceof DefaultPersistent)) {
            if (StringUtils.isEmpty(entity.getObjectId())) {
                entity.setObjectId(Identities.uuid2());
            }
            return super.save(entity);
        }

        S current = (S) getOne(entity.getObjectId());

        // 可能是手动设置的objectId
        if (current == null) {
            return super.save(entity);
        }

        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(entity.getClass());
        List<String> ignoreList = null;
        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(current.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(entity);
                            if (value == null) {
                                continue;
                            }
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(current, value);
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }

        return current;
    }

    public void logicDelete(String objectId) {

        T entity = getOne(objectId);
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

        Pageable pageable = PageRequest.of(queryInfo.getPageInfo().getPage() - 1, queryInfo.getPageInfo().getPageSize(), queryInfo.getSort());
        Predicate expression = pretreatmentPredicate(queryInfo.getPredicate());
        return this.findAll(expression, pageable);
    }

    public List<T> findAll(Predicate predicate) {

        return super.findAll(pretreatmentPredicate(predicate));
    }

    public List<T> findAll(Predicate predicate, Sort sort) {

        return super.findAll(pretreatmentPredicate(predicate), sort);
    }

    public List<T> findAll(Predicate predicate, OrderSpecifier... orderSpecifiers) {

        return super.findAll(pretreatmentPredicate(predicate), orderSpecifiers);
    }

    public List<T> findAll() {

        Predicate predicate = pretreatmentPredicate(null);
        if (predicate == null) {
            return super.findAll();
        } else {
            return super.findAll(predicate);
        }
    }

    public long count(Predicate predicate) {

        return super.count(pretreatmentPredicate(predicate));
    }

    public List<BasicInfo> list() {

        Query query = this.entityManager.createQuery("select a.objectId, a.name from " + entityInformation.getJavaType().getName()
                + " a where a.deleted = 0");
        return ((List<Object[]>) query.getResultList())
                .stream()
                .map(data -> BasicInfo.valueOf((String) data[0], (String) (data)[1]))
                .collect(toList());
    }


    public T findRoot() {

        return findRoot(null);
    }

    public T findRoot(String tag) {

        Query query = this.entityManager.createQuery("select a from " + entityInformation.getJavaType().getName()
                + " a where a.parent is null " + (tag == null ? "" : " and a.tag = '" + tag + "'") + " and a.deleted = 0");

        List resultList = query.getResultList();
        if (CollectionUtils.isEmpty(resultList)) {
            return null;
        }
        return (T) resultList.get(0);
    }

    private Predicate pretreatmentPredicate(Predicate predicate) {

        EntityPath path = DEFAULT_ENTITY_PATH_RESOLVER.createPath(entityInformation.getJavaType());
        Field deleted = ReflectionUtils.findField(path.getClass(), "deleted");
        if (deleted != null) {
            BooleanExpression expression = ((NumberPath) ReflectionUtils.getField(ReflectionUtils.findField(path.getClass(), "deleted"), path)).eq(0);
            if (predicate != null) {
                expression = expression.and(predicate);
            }
            return expression;
        }
        return predicate;
    }
}

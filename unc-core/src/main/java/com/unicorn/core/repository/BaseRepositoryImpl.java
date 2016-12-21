package com.unicorn.core.repository;

import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.path.NumberPath;
import com.unicorn.core.domain.DefaultPersistent;
import com.unicorn.core.domain.Identifiable;
import com.unicorn.core.domain.Persistent;
import com.unicorn.core.query.QueryInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
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

public class BaseRepositoryImpl<T extends Identifiable> extends QueryDslJpaRepository<T, String> implements BaseRepository<T> {

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

    public <S extends T> S save(S entity) {

        if (StringUtils.isEmpty(entity.getObjectId())) {
            return super.save(entity);
        }

        if (!(entity instanceof DefaultPersistent)) {
            return super.save(entity);
        }

        S current = (S) findOne(entity.getObjectId());

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

        // todo  QueryDslJpaRepository.java  line.173

        Pageable pageable = new PageRequest(queryInfo.getPageInfo().getPageNo() - 1, queryInfo.getPageInfo().getPageSize(), queryInfo.getSort());
        EntityPath path = DEFAULT_ENTITY_PATH_RESOLVER.createPath(entityInformation.getJavaType());
        Field deleted = ReflectionUtils.findField(path.getClass(), "deleted");
        Predicate expression = queryInfo.getPredicate();
        if (deleted != null) {
            expression = ((NumberPath) ReflectionUtils.getField(ReflectionUtils.findField(path.getClass(), "deleted"), path)).eq(0)
                    .and(queryInfo.getPredicate());
        }
        return this.findAll(expression, pageable);
    }

    public List<T> findAll() {

        EntityPath path = DEFAULT_ENTITY_PATH_RESOLVER.createPath(entityInformation.getJavaType());
        Field deleted = ReflectionUtils.findField(path.getClass(), "deleted");
        if (deleted != null) {
            Predicate expression = ((NumberPath) ReflectionUtils.getField(ReflectionUtils.findField(path.getClass(), "deleted"), path)).eq(0);
            return super.findAll(expression);
        } else {
            return super.findAll();
        }
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

}

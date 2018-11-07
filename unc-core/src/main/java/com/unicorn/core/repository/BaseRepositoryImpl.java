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
import javax.persistence.Table;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map move(String objectId, String targetId, Integer position) {

        Map result = new HashMap();

        if (objectId.equals(targetId)) {
            return result;
        }

        // 获取当前操作的表名
        String annotationInfo = this.entityInformation.getJavaType().getAnnotation(Table.class).toString();
        String tableName = org.apache.commons.lang3.StringUtils.substringBetween(annotationInfo, "name=", ",");

        String sql = "select a.parent_id, a.order_no from " + tableName + " a where a.objectid = ?";

        String parentId, targetParentId;
        Integer orderNo, targetOrderNo;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, objectId);
        Object[] singleResult = (Object[]) query.getSingleResult();
        parentId = (String) singleResult[0];
        orderNo = (Integer) singleResult[1];

        query.setParameter(1, targetId);
        singleResult = (Object[]) query.getSingleResult();
        targetParentId = (String) singleResult[0];
        targetOrderNo = (Integer) singleResult[1];

        // 移动到目标节点下面
        if (position == 1) {
            targetOrderNo++;
        }

        result.put("parentId", parentId);
        result.put("targetParentId", targetParentId);
        result.put("targetOrderNo", targetOrderNo);

        Query updateQuery = entityManager.createNativeQuery("update " + tableName + " set parent_id = ?, order_no = ? where objectid = ?");
        Query plusOrderNoQuery = entityManager.createNativeQuery("update " + tableName + " set order_no = order_no + 1 where parent_id = ? and order_no >= ?");
        Query minusOrderNoQuery = entityManager.createNativeQuery("update " + tableName + " set order_no = order_no - 1 where parent_id = ? and order_no > ?");

        // TODO 过滤移动到子节点

        // 在同一父节点下移动
        if (parentId.equals(targetParentId) && position != 0) {
            if (targetOrderNo == orderNo) {
                return result;
            }
            // 向上移动
            if (targetOrderNo < orderNo) {
                Query nativeQuery = entityManager.createNativeQuery("update " + tableName + " set order_no = order_no + 1" +
                        " where parent_id = ? and order_no >= ? and order_no < ?");
                nativeQuery.setParameter(1, parentId);
                nativeQuery.setParameter(2, targetOrderNo);
                nativeQuery.setParameter(3, orderNo);
                nativeQuery.executeUpdate();
            }
            // 向下移动
            else {
                targetOrderNo--;
                Query nativeQuery = entityManager.createNativeQuery("update " + tableName + " set order_no = order_no - 1" +
                        " where parent_id = ? and order_no > ? and order_no <= ?");
                nativeQuery.setParameter(1, parentId);
                nativeQuery.setParameter(2, orderNo);
                nativeQuery.setParameter(3, targetOrderNo);
                nativeQuery.executeUpdate();
            }
            result.put("targetOrderNo", targetOrderNo);

            // 更新当前节点
            updateQuery.setParameter(1, parentId);
            updateQuery.setParameter(2, targetOrderNo);
            updateQuery.setParameter(3, objectId);
            updateQuery.executeUpdate();
            return result;
        }

        // 移动到父节点（不做任何处理）
        if (parentId.equals(targetId) && position == 0) {
            return result;
        }

        // 移动到目标节点里面
        if (position == 0) {

            // 获取目标节点下最大orderNo
            Query nativeQuery = entityManager.createNativeQuery("select max(order_no) from " + tableName + " where parent_id = ?");
            nativeQuery.setParameter(1, targetId);
            Integer maxOrderNo = (Integer) nativeQuery.getSingleResult();
            maxOrderNo = maxOrderNo == null ? 0 : maxOrderNo;

            // 更新当前节点
            updateQuery.setParameter(1, targetId);
            updateQuery.setParameter(2, maxOrderNo + 1);
            updateQuery.setParameter(3, objectId);
            updateQuery.executeUpdate();

            result.put("targetParentId", targetId);
            result.put("targetOrderNo", maxOrderNo + 1);
        } else {

            // 更新排序号
            plusOrderNoQuery.setParameter(1, targetParentId);
            plusOrderNoQuery.setParameter(2, targetOrderNo);
            plusOrderNoQuery.executeUpdate();

            // 更新当前节点
            updateQuery.setParameter(1, targetParentId);
            updateQuery.setParameter(2, targetOrderNo);
            updateQuery.setParameter(3, objectId);
            updateQuery.executeUpdate();
        }

        // 更新原始父节点下子节点的排序号
        minusOrderNoQuery.setParameter(1, parentId);
        minusOrderNoQuery.setParameter(2, orderNo);
        minusOrderNoQuery.executeUpdate();
        return result;
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

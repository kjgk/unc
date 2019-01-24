package com.unicorn.system.repository;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Code;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeRepository extends BaseRepository<Code> {

    @Query("select a from Code a where a.tag = ?1 and a.deleted = 0")
    List<Code> findByTag(String tag);

    @Query("select a from Code a where a.parent.tag = ?1 and a.tag = ?2 and a.deleted = 0")
    List<Code> findByEnum(String parentTag, String tag);

    @Query("select max(a.orderNo) from Code a where a.parent.objectId = ?1 and a.deleted = 0")
    Integer findMaxOrderNo(Long parentId);

    @Modifying
    @Query("update Code a set a.orderNo = a.orderNo - 1 where a.parent.objectId = ?1 and a.orderNo > ?2 and a.deleted = 0")
    Integer minusOrderNo(Long parentId, Integer orderNo);

}

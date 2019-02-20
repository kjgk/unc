package com.unicorn.core.repository;

import com.unicorn.common.repository.BaseRepository;
import com.unicorn.common.domain.po.Menu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MenuRepository extends BaseRepository<Menu> {

    @Query("select max(a.orderNo) from Menu a where a.parent.objectId = ?1 and a.deleted = 0")
    Integer findMaxOrderNo(Long parentId);

    @Modifying
    @Query("update Menu a set a.orderNo = a.orderNo - 1 where a.parent.objectId = ?1 and a.orderNo > ?2 and a.deleted = 0")
    Integer minusOrderNo(Long parentId, Integer orderNo);
}

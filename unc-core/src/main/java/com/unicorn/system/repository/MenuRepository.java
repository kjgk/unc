package com.unicorn.system.repository;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Menu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MenuRepository extends BaseRepository<Menu> {

    @Query("select max(a.orderNo) from Menu a where a.parent.objectId = ?1 and a.deleted = 0")
    Integer findMaxOrderNo(String parentId);

    @Modifying
    @Query("update Menu a set a.orderNo = a.orderNo - 1 where a.parent.objectId = ?1 and a.orderNo > ?2 and a.deleted = 0")
    Integer minusOrderNo(String parentId, Integer orderNo);
}

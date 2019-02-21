package com.unicorn.common.repository;

import com.unicorn.common.domain.po.RoleMenu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleMenuRepository extends CrudRepository<RoleMenu, String> {

    @Modifying
    @Query("delete from RoleMenu a where a.role.objectId = ?1")
    void deleteByRoleId(Long roleId);

    @Query("select a from RoleMenu a where a.role.objectId = ?1 and a.menu.deleted = 0")
    List<RoleMenu> findByRoleId(Long roleId);
}

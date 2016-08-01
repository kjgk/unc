package com.unicorn.system.repository;

import com.unicorn.system.domain.po.RoleMenu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleMenuRepository extends CrudRepository<RoleMenu, String> {

    @Modifying
    @Query("delete from RoleMenu a where a.role.objectId = ?1")
    void deleteByRoleId(String roleId);

    @Query("select a from RoleMenu a where a.role.objectId = ?1 and a.role.deleted = 0 and a.menu.deleted = 0")
    List<RoleMenu> findByRoleId(String roleId);
}

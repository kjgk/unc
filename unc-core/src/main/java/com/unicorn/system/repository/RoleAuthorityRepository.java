package com.unicorn.system.repository;

import com.unicorn.system.domain.po.RoleAuthority;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleAuthorityRepository extends CrudRepository<RoleAuthority, String> {

    @Modifying
    @Query("delete from RoleAuthority a where a.role.objectId = ?1")
    void deleteByRoleId(String roleId);

    @Query("select a from RoleAuthority a where a.role.objectId = ?1 and a.authority.deleted = 0")
    List<RoleAuthority> findByRoleId(String roleId);
}

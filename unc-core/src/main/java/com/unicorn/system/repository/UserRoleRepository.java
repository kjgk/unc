package com.unicorn.system.repository;

import com.unicorn.system.domain.po.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRole, String> {

    @Query("select a from UserRole a where a.user.objectId = ?1")
    List<UserRole> findByUserId(String userId);

    @Query("select a from UserRole a where a.role.objectId = ?1")
    List<UserRole> findByRoleId(String roleId);
}

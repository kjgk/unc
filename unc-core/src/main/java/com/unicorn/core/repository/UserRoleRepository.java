package com.unicorn.core.repository;

import com.unicorn.core.domain.po.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRole, String> {

    @Query("select a from UserRole a where a.user.objectId = ?1 and a.role.deleted = 0")
    List<UserRole> findByUserId(Long userId);

    @Query("select a from UserRole a where a.role.objectId = ?1 and a.user.deleted = 0")
    List<UserRole> findByRoleId(Long roleId);
}

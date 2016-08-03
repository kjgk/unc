package com.unicorn.system.repository;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Role;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends BaseRepository<Role> {

    @Query("select a from Role a where a.tag = ?1 and a.deleted = 0")
    Role findByTag(String roleTag);
}

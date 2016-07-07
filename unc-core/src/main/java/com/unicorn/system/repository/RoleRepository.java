package com.unicorn.system.repository;

import com.unicorn.system.domain.po.Role;
import com.unicorn.core.repository.BaseRepository;

public interface RoleRepository extends BaseRepository<Role> {

    Role findByTag(String roleTag);
}

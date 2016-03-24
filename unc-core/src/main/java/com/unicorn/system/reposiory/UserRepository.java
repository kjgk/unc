package com.unicorn.system.reposiory;

import com.unicorn.system.domain.po.User;
import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.UserRole;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends BaseRepository<User> {

}

package com.unicorn.system.reposiory;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Account;
import com.unicorn.system.domain.po.User;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends BaseRepository<Account> {

    @Query(value = "select a from Account a where a.name = ?1")
    Account findByName(String s);
}

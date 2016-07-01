package com.unicorn.system.repository;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Account;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends BaseRepository<Account> {

    @Query(value = "select a from Account a where a.name = ?1")
    Account findByName(String s);
}

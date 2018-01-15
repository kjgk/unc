package com.unicorn.system.repository;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends BaseRepository<Account> {

    @Query("select a from Account a where a.name = ?1 and a.user.deleted = 0")
    List<Account> findByName(String s);

    @Modifying
    @Query("delete from Account a where a.user.objectId = ?1")
    void deleteByUserId(String s);
}

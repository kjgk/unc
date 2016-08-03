package com.unicorn.system.repository;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Code;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeRepository extends BaseRepository<Code> {

    @Query("select a from Code a where a.tag = ?1 and a.deleted = 0")
    List<Code> findByTag(String tag);

    @Query("select a from Code a where a.parent.tag = ?1 and a.tag = ?2 and a.deleted = 0")
    List<Code> findByEnum(String parentTag, String tag);

}

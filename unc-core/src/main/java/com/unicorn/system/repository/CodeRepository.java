package com.unicorn.system.repository;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Code;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeRepository extends BaseRepository<Code> {

    List<Code> findByParent(Code parent);

    List<Code> findByTag(String tag);

    @Query(value = "select c from Code c where c.parent.tag = ?1 and c.tag = ?2")
    List<Code> findByEnum(String parentTag, String tag);

}

package com.unicorn.system.reposiory;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Code;

import java.util.List;

public interface CodeRepository extends BaseRepository<Code> {

    List<Code> findByParent(Code parent);

    List<Code> findByTag(String tag);
}

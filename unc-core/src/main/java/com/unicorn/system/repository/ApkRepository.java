package com.unicorn.system.repository;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Apk;


public interface ApkRepository extends BaseRepository<Apk> {

    Apk findByTag(String tag);
}

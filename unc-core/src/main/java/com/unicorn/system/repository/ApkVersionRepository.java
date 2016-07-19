package com.unicorn.system.repository;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.ApkVersion;


public interface ApkVersionRepository extends BaseRepository<ApkVersion> {

    ApkVersion findByVersion(String version);

    ApkVersion findByCode(String code);
}

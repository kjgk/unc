package com.unicorn.system.repository;

import com.unicorn.system.domain.po.ApkVersion;
import org.springframework.data.repository.CrudRepository;


public interface ApkVersionRepository extends CrudRepository<ApkVersion, String> {

    ApkVersion findByVersion(String version);

    ApkVersion findByCode(String code);
}

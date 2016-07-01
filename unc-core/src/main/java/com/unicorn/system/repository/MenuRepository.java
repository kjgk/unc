package com.unicorn.system.repository;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Menu;

import java.util.List;

public interface MenuRepository extends BaseRepository<Menu> {

    List<Menu> findByParent(Menu parent);
}

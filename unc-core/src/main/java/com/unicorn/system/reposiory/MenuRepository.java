package com.unicorn.system.reposiory;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Menu;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends BaseRepository<Menu> {

    List<Menu> findByParent(Menu parent);
}

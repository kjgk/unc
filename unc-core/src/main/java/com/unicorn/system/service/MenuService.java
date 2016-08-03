package com.unicorn.system.service;

import com.unicorn.system.domain.po.Menu;
import com.unicorn.system.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;


    public Menu getRootMenu() {

        return menuRepository.findRoot();
    }

    public Menu getMenu(String id) {

        return menuRepository.findOne(id);
    }

    public void saveMenu(Menu menu) {

        menuRepository.save(menu);
    }

    public void deleteMenu(String objectId) {

        menuRepository.logicDelete(objectId);
    }

}

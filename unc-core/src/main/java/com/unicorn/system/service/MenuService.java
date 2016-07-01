package com.unicorn.system.service;

import com.unicorn.system.domain.po.Menu;
import com.unicorn.system.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;


    public Menu getRootMenu() {

        return menuRepository.findByParent(null).get(0);
    }


    public Menu getMenu(String id) {

        return menuRepository.findOne(id);
    }

    public void saveMenu(Menu menu) {

        if (StringUtils.isEmpty(menu.getObjectId())) {
            menuRepository.save(menu);
        } else {
            getMenu(menu.getObjectId()).merge(menu);
        }
    }

    public void deleteMenu(String objectId) {

        menuRepository.logicDelete(objectId);
    }

}

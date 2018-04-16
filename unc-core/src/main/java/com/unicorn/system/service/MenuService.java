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

        return menuRepository.findRoot();
    }

    public Menu getMenu(String id) {

        return menuRepository.findOne(id);
    }

    public Menu saveMenu(Menu menu) {

        Menu current;
        if (StringUtils.isEmpty(menu.getObjectId())) {
            current = menuRepository.save(menu);
        } else {
            current = menuRepository.findOne(menu.getObjectId());
            current.setName(menu.getName());
            current.setTag(menu.getTag());
            current.setOrderNo(menu.getOrderNo());
            current.setDescription(menu.getDescription());
            current.setEnabled(menu.getEnabled());
            current.setHidden(menu.getHidden());
            current.setIcon(menu.getIcon());
            current.setUrl(menu.getUrl());
            if (menu.getParent() != null) {
                current.setParent(menu.getParent());
            }
        }
        return current;
    }

    public void deleteMenu(String objectId) {

        menuRepository.logicDelete(objectId);
    }

}

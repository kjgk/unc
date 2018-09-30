package com.unicorn.system.service;

import com.unicorn.system.domain.po.Menu;
import com.unicorn.system.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;


    public List<Menu> getAll() {

        return menuRepository.findAll();
    }

    public Menu getRootMenu() {

        return menuRepository.findRoot();
    }

    public Menu getMenu(String id) {

        return menuRepository.get(id);
    }

    public Menu saveMenu(Menu menu) {

        Menu current;
        if (StringUtils.isEmpty(menu.getObjectId())) {
            current = menuRepository.save(menu);
        } else {
            current = menuRepository.get(menu.getObjectId());
            current.setName(menu.getName());
            current.setTag(menu.getTag());
            current.setDescription(menu.getDescription());
            current.setEnabled(menu.getEnabled());
            current.setHidden(menu.getHidden());
            current.setIcon(menu.getIcon());
            current.setUrl(menu.getUrl());
        }
        return current;
    }

    public void deleteMenu(String objectId) {

        Menu menu = menuRepository.get(objectId);
        if (!CollectionUtils.isEmpty(menu.getChildList())) {
            for (Menu child : menu.getChildList()) {
                deleteMenu(child.getObjectId());
            }
        }
        menuRepository.deleteById(objectId);
    }
}

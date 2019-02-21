package com.unicorn.core.service;

import com.unicorn.common.domain.po.Menu;
import com.unicorn.common.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

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

    public Menu getMenu(Long objectId) {

        return menuRepository.get(objectId);
    }

    public Menu saveMenu(Menu menu) {

        Menu current;
        if (StringUtils.isEmpty(menu.getObjectId())) {
            Integer maxOrderNo = menuRepository.findMaxOrderNo(menu.getParentId());
            menu.setOrderNo(maxOrderNo == null ? 1 : maxOrderNo + 1);
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

    public void deleteMenu(Long objectId) {

        Menu menu = menuRepository.get(objectId);
        if (!CollectionUtils.isEmpty(menu.getChildList())) {
            for (Menu child : menu.getChildList()) {
                deleteMenu(child.getObjectId());
            }
        }
        menuRepository.deleteById(objectId);

        menuRepository.minusOrderNo(menu.getParentId(), menu.getOrderNo());
    }

    public Map moveMenu(Long objectId, Long targetId, Integer position) {

        return menuRepository.move(objectId, targetId, position);
    }
}

package com.unicorn.system.web.controller;

import com.unicorn.system.domain.po.Menu;
import com.unicorn.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List loadMenuTree(String id
            , @RequestParam(defaultValue = "false") Boolean fetchChild, @RequestParam(defaultValue = "true") Boolean fetchRoot) {

        List result;

        // 获取全部
        if (StringUtils.isEmpty(id)) {
            Menu menu = menuService.getRootMenu();
            result = convertMenuData(menu, true);
        }
        // 根据ID获取
        else {
            Menu menu;
            if (StringUtils.isEmpty(id) || TREE_NODE_ROOT.equals(id)) {
                if (fetchRoot) {
                    menu = new Menu();
                    menu.setChildList(new ArrayList());
                    menu.getChildList().add(menuService.getRootMenu());
                } else {
                    menu = menuService.getRootMenu();
                }
            } else {
                menu = menuService.getMenu(id);
            }
            result = convertMenuData(menu, fetchChild);
        }
        return result;
    }

    private List convertMenuData(Menu menu, Boolean fetchChild) {
        List result = new ArrayList();
        if (!CollectionUtils.isEmpty(menu.getChildList())) {
            for (Menu child : menu.getChildList()) {
                Map data = new HashMap();
                data.put("id", child.getObjectId());
                data.put("objectId", child.getObjectId());
                data.put("name", child.getName());
                data.put("tag", child.getTag());
                data.put("url", child.getUrl());
                data.put("icon", child.getIcon());
                data.put("orderNo", child.getOrderNo());
                data.put("leaf", CollectionUtils.isEmpty(child.getChildList()) ? 1 : 0);
                if (fetchChild) {
                    data.put("childList", convertMenuData(child, fetchChild));
                }
                result.add(data);
            }
        }
        return result;
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
    public Menu get(@PathVariable String objectId) {

        return menuService.getMenu(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Menu create(@RequestBody Menu menu) {

        menuService.saveMenu(menu);
        return menu;
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.PUT)
    public void update(@RequestBody Menu menu, @PathVariable String objectId) {

        menuService.saveMenu(menu);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("objectId") String objectId) {
        menuService.deleteMenu(objectId);
    }
}

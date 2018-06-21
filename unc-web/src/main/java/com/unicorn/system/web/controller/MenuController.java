package com.unicorn.system.web.controller;

import com.unicorn.base.web.BaseController;
import com.unicorn.system.domain.po.Menu;
import com.unicorn.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.unicorn.base.web.ApiNamespace.API_V1;

@RestController
@RequestMapping(API_V1 + "/system/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List loadMenuTree(String id
            , @RequestParam(defaultValue = "false") Boolean fetchChild
            , @RequestParam(defaultValue = "false") Boolean backward
    ) {

        if (backward) {
            return buildTreeDataBackward(menuService.getMenu(id));
        }

        // 获取全部
        if (StringUtils.isEmpty(id)) {
            Menu menu = menuService.getRootMenu();
            return buildTreeData(menu.getChildList(), true);
        }
        // 根据ID获取
        else {
            Menu menu;
            if ("root".equalsIgnoreCase(id)) {
                menu = new Menu();
                menu.setChildList(new ArrayList());
                menu.getChildList().add(menuService.getRootMenu());
            } else {
                menu = menuService.getMenu(id);
            }
            return buildTreeData(menu.getChildList(), fetchChild);
        }
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
    public Menu get(@PathVariable String objectId) {

        return menuService.getMenu(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Menu create(@RequestBody Menu menu) {

        return menuService.saveMenu(menu);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.PATCH)
    public void update(@RequestBody Menu menu, @PathVariable String objectId) {

        menuService.saveMenu(menu);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("objectId") String objectId) {
        menuService.deleteMenu(objectId);
    }
}

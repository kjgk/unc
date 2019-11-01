package com.unicorn.system.web.controller;

import com.unicorn.base.web.BaseController;
import com.unicorn.core.domain.po.Menu;
import com.unicorn.system.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.unicorn.base.web.ApiNamespace.API_V1;

@RestController
@RequestMapping(API_V1 + "/system/menu")
@AllArgsConstructor
@Secured("ROLE_ADMIN")
public class MenuController extends BaseController {

    private MenuService menuService;

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List loadMenuTree(@RequestParam(value = "id", required = false) Long objectId
            , @RequestParam(defaultValue = "false") Boolean fetchChild
            , @RequestParam(defaultValue = "false") Boolean backward
    ) {

        if (backward) {
            return buildTreeDataBackward(menuService.getMenu(objectId));
        }

        // 获取全部
        if (StringUtils.isEmpty(objectId)) {
            Menu menu = menuService.getRootMenu();
            return buildTreeData(menu.getChildList(), true);
        }
        // 根据ID获取
        else {
            Menu menu;
            if (objectId == -1) {
                menu = new Menu();
                menu.setChildList(new ArrayList());
                menu.getChildList().add(menuService.getRootMenu());
            } else {
                menu = menuService.getMenu(objectId);
            }
            return buildTreeData(menu.getChildList(), fetchChild);
        }
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
    public Menu get(@PathVariable Long objectId) {

        return menuService.getMenu(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Menu create(@RequestBody Menu menu) {

        return menuService.saveMenu(menu);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.PATCH)
    public void update(@RequestBody Menu menu, @PathVariable Long objectId) {

        menuService.saveMenu(menu);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("objectId") Long objectId) {
        menuService.deleteMenu(objectId);
    }

    @RequestMapping(value = "/{objectId}/move", method = RequestMethod.POST)
    public Map move(@RequestBody Map params, @PathVariable Long objectId) {

        return menuService.moveMenu(objectId, Long.valueOf(params.get("targetId").toString()), (Integer) params.get("position"));
    }
}

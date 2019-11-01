package com.unicorn.system.web.controller;

import com.unicorn.base.web.BaseController;
import com.unicorn.core.domain.po.Code;
import com.unicorn.system.service.CodeService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.unicorn.base.web.ApiNamespace.API_V1;

@RestController
@RequestMapping(API_V1 + "/system/code")
@AllArgsConstructor
@Secured("ROLE_ADMIN")
public class CodeController extends BaseController {

    private CodeService codeService;

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List loadCodeTree(@RequestParam(value = "id", required = false) Long objectId, String tag
            , @RequestParam(defaultValue = "false") Boolean fetchChild
            , @RequestParam(defaultValue = "false") Boolean backward
    ) {

        if (backward) {
            return buildTreeDataBackward(codeService.getCode(objectId));
        }

        // 根据标识获取
        if (!StringUtils.isEmpty(tag)) {
            Code code = codeService.getCodeByTag(tag);
            return buildTreeData(code.getChildList(), fetchChild);
        }
        // 获取全部
        if (StringUtils.isEmpty(objectId)) {
            Code code = codeService.getRootCode();
            return buildTreeData(code.getChildList(), true);
        }
        // 根据ID获取
        else {
            Code code;
            if (objectId == -1) {
                code = new Code();
                code.setChildList(new ArrayList());
                code.getChildList().add(codeService.getRootCode());
            } else {
                code = codeService.getCode(objectId);
            }
            return buildTreeData(code.getChildList(), fetchChild);
        }
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
    public Code get(@PathVariable Long objectId) {

        return codeService.getCode(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Code create(@RequestBody Code code) {

        return codeService.saveCode(code);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.PATCH)
    public void update(@RequestBody Code code, @PathVariable Long objectId) {

        codeService.saveCode(code);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("objectId") Long objectId) {
        codeService.deleteCode(objectId);
    }

    @RequestMapping(value = "/{objectId}/move", method = RequestMethod.POST)
    public Map move(@RequestBody Map params, @PathVariable Long objectId) {

        return codeService.moveCode(objectId, Long.valueOf(params.get("targetId").toString()), (Integer) params.get("position"));
    }
}

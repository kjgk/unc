package com.unicorn.system.web.controller;

import com.unicorn.base.web.BaseController;
import com.unicorn.system.domain.po.Code;
import com.unicorn.system.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system/code")
public class CodeController extends BaseController {

    @Autowired
    private CodeService codeService;

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List loadCodeTree(String id, String tag
            , @RequestParam(defaultValue = "false") Boolean fetchChild
            , @RequestParam(defaultValue = "false") Boolean backward
    ) {

        if (backward) {
            return buildTreeDataBackward(codeService.getCode(id));
        }

        // 根据标识获取
        if (!StringUtils.isEmpty(tag)) {
            Code code = codeService.getCodeByTag(tag);
            return buildTreeData(code.getChildList(), fetchChild);
        }
        // 获取全部
        if (StringUtils.isEmpty(id)) {
            Code code = codeService.getRootCode();
            return buildTreeData(code.getChildList(), true);
        }
        // 根据ID获取
        else {
            Code code;
            if ("root".equalsIgnoreCase(id)) {
                code = new Code();
                code.setChildList(new ArrayList());
                code.getChildList().add(codeService.getRootCode());
            } else {
                code = codeService.getCode(id);
            }
            return buildTreeData(code.getChildList(), fetchChild);
        }
    }


    @RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
    public Code get(@PathVariable String objectId) {

        return codeService.getCode(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Code create(@RequestBody Code code) {

        return codeService.saveCode(code);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.PATCH)
    public void update(@RequestBody Code code, @PathVariable String objectId) {

        codeService.saveCode(code);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("objectId") String objectId) {
        codeService.deleteCode(objectId);
    }
}

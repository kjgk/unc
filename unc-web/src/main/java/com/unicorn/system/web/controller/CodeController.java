package com.unicorn.system.web.controller;

import com.unicorn.system.domain.po.Code;
import com.unicorn.system.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List loadCodeTree(String id, String tag
            , @RequestParam(defaultValue = "false") Boolean fetchChild, @RequestParam(defaultValue = "true") Boolean fetchRoot) {

        List result;

        // 根据标识获取
        if (!StringUtils.isEmpty(tag)) {
            Code code = codeService.getCodeByTag(tag);
            if (code != null && code.getChildList() != null) {
                return convertCodeData(code, fetchChild);
            }
        }

        // 获取全部
        if (StringUtils.isEmpty(id)) {
            Code code = codeService.getRootCode();
            result = convertCodeData(code, true);
        }
        // 根据ID获取
        else {
            Code code;
            if (StringUtils.isEmpty(id) || "ROOT".equalsIgnoreCase(id)) {
                if (fetchRoot) {
                    code = new Code();
                    code.setChildList(new ArrayList());
                    code.getChildList().add(codeService.getRootCode());
                } else {
                    code = codeService.getRootCode();
                }
            } else {
                code = codeService.getCode(id);
            }
            result = convertCodeData(code, fetchChild);
        }
        return result;
    }

    private List convertCodeData(Code code, Boolean fetchChild) {
        List result = new ArrayList();
        if (!CollectionUtils.isEmpty(code.getChildList())) {
            for (Code child : code.getChildList()) {
                Map data = new HashMap();
                data.put("id", child.getObjectId());
                data.put("objectId", child.getObjectId());
                data.put("name", child.getName());
                data.put("tag", child.getTag());
                data.put("orderNo", child.getOrderNo());
                data.put("leaf", CollectionUtils.isEmpty(child.getChildList()) ? 1 : 0);
                if (fetchChild) {
                    data.put("childList", convertCodeData(child, fetchChild));
                }
                result.add(data);
            }
        }
        return result;
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
    public Code get(@PathVariable String objectId) {

        return codeService.getCode(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Code create(@RequestBody Code code) {

        return codeService.saveCode(code);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.PUT)
    public void update(@RequestBody Code code, @PathVariable String objectId) {

        codeService.saveCode(code);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("objectId") String objectId) {
        codeService.deleteCode(objectId);
    }
}

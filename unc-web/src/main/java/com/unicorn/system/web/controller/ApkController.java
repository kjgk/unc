package com.unicorn.system.web.controller;

import com.mysema.query.types.expr.BooleanExpression;
import com.unicorn.core.query.PageInfo;
import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.domain.po.Apk;
import com.unicorn.system.domain.po.ApkVersion;
import com.unicorn.system.domain.po.QApk;
import com.unicorn.system.service.ApkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/system/apk")
public class ApkController extends BaseController {

    @Autowired
    private ApkService apkService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Apk> list(PageInfo pageInfo, String keyword) {

        QApk apk = QApk.apk;
        BooleanExpression expression = apk.isNotNull();
        if (!StringUtils.isEmpty(keyword)) {
            expression = expression.and(apk.name.containsIgnoreCase(keyword).or(apk.tag.containsIgnoreCase(keyword)));
        }
        QueryInfo queryInfo = new QueryInfo(expression, pageInfo, new Sort(Sort.Direction.ASC, "name"));
        return apkService.getApk(queryInfo);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
    public Apk get(@PathVariable("objectId") String objectId) {

        return apkService.getApk(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Apk create(@RequestBody Apk apk) {

        apkService.saveApk(apk);
        return apk;
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.PUT)
    public void update(@RequestBody Apk apk, @PathVariable String objectId) {

        apkService.saveApk(apk);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("objectId") String objectId) {
        apkService.deleteApk(objectId);
    }

    @RequestMapping(value = "/{objectId}/version", method = RequestMethod.PUT)
    public void saveApkVersion(@RequestBody ApkVersion apkVersion, @PathVariable(value = "objectId") String apkId) throws IOException {

        apkVersion.setApkId(apkId);
        apkService.saveApkVersion(apkVersion);
    }

    /**
     * 自动更新apk
     */
    @RequestMapping(value = "/{tag}/update", method = RequestMethod.GET)
    public Map getNewestVersion(@PathVariable("tag") String tag) throws IOException {

        Map result = new HashMap();
        ApkVersion apkVersion = apkService.getNewestVersion(tag);
        result.put("version", apkVersion == null ? "" : apkVersion.getVersion());
        result.put("code", apkVersion == null ? "" : apkVersion.getCode());
        return result;
    }
}

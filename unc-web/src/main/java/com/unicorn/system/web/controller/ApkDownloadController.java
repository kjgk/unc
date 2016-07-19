package com.unicorn.system.web.controller;

import com.unicorn.system.domain.po.Apk;
import com.unicorn.system.service.ApkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;


@RestController
@RequestMapping(value = "/apk/download")
public class ApkDownloadController extends BaseController {

    @Autowired
    private ApkService apkService;

    @RequestMapping(value = "/{code}")
    public void download(@PathVariable(value = "code") String code, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Apk apk = apkService.getApkByCode(code);
        File file = apkService.getFileByCode(apk.getVersionList().get(0).getCode());
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException("系统找不到指定文件！");
        }

        String fileName = apk.getName() + ".apk";

        response.setHeader("Content-Length", file.length() + "");
        response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/vnd.android.package-archive");

        FileCopyUtils.copy(fileInputStream, response.getOutputStream());

        fileInputStream.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}

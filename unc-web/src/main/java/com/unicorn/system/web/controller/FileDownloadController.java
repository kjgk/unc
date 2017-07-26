package com.unicorn.system.web.controller;

import com.unicorn.system.service.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;


@RestController
@RequestMapping(value = "/system/file/download")
public class FileDownloadController extends BaseController {

    @Autowired
    private EnvironmentService environmentService;

    @RequestMapping(method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String filename = request.getParameter("filename");
        String tempFilename = request.getParameter("tempFilename");
        String type = request.getParameter("type");

        File file = null;
        if (!StringUtils.isEmpty(tempFilename)) {
            file = new File(environmentService.getTempPath() + '/' + tempFilename);
        }
        if (!StringUtils.isEmpty(filename)) {
            file = new File(environmentService.getUploadPath() + '/' + filename);
        }

        FileInputStream fileInputStream = new FileInputStream(file);

        response.setHeader("Content-Length", file.length() + "");
        if ("image".equals(type)) {
            response.setContentType("image/jpg");
        } else {
            response.setContentType("application/octet-stream");
        }

        FileCopyUtils.copy(fileInputStream, response.getOutputStream());
        fileInputStream.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

}

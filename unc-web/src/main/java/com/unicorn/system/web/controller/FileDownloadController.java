package com.unicorn.system.web.controller;

import com.unicorn.core.service.EnvironmentService;
import lombok.AllArgsConstructor;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import static com.unicorn.base.web.ApiNamespace.API_V1;

@RestController
@RequestMapping(API_V1 + "/system/file/download")
@AllArgsConstructor
public class FileDownloadController {

    private EnvironmentService environmentService;

    @GetMapping
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String filename = request.getParameter("filename");
        String tempFilename = request.getParameter("tempFilename");
        String type = request.getParameter("type");
        String tempFilePath = environmentService.getTempPath() + '/' + tempFilename;
        File file = new File(tempFilePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        response.setHeader("Content-Length", file.length() + "");
        if (filename != null) {
            response.setHeader("Content-Disposition", "filename=" + new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        }
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

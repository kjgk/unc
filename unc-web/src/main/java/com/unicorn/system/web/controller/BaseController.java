package com.unicorn.system.web.controller;

import com.unicorn.core.DateEditor;
import com.unicorn.core.userdetails.UserDetail;
import com.unicorn.system.domain.po.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class BaseController {

    protected static final String TREE_NODE_ROOT = "Root";

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    protected User getCurrentUser() {

        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail.getUser();
    }

    protected void downloadFile(HttpServletResponse response, File file) throws IOException {

        downloadFile(response, file, null, null);
    }

    protected void downloadFile(HttpServletResponse response, File file, String filename) throws IOException {

        String type = "";
        if (StringUtils.isNotEmpty(filename)) {
            String extension = FilenameUtils.getExtension(filename).toLowerCase();
            if ("mp3;wav,ogg,ape,acc".indexOf(extension) != -1) {
                type = "Audio";
            } else if ("mp4;avi,wmv,mkv".indexOf(extension) != -1) {
                type = "Video";
            } else if ("jpg,jpeg,bmp,gif,png".indexOf(extension) != -1) {
                type = "Picture";
            }
        }
        downloadFile(response, file, filename, type);
    }

    protected void downloadFile(HttpServletResponse response, File file, String filename, String type) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);

        String contentType = "application/octet-stream";

        if ("Picture".equalsIgnoreCase(type)) {
            contentType = "image/jpg";
        }
        if ("Audio".equalsIgnoreCase(type)) {
            contentType = "audio/mp3";
        }
        if ("Video".equalsIgnoreCase(type)) {
            contentType = "video/mp4";
        }

        response.setHeader("Content-Length", file.length() + "");
        if (StringUtils.isNotEmpty(filename)) {
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes("GBK"), "ISO-8859-1"));
        }
        response.setContentType(contentType);

        FileCopyUtils.copy(fileInputStream, response.getOutputStream());

        fileInputStream.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}

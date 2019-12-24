package com.unicorn.std.controller;

import com.unicorn.core.service.EnvironmentService;
import com.unicorn.std.domain.po.Attachment;
import com.unicorn.std.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/content/download")
public class ContentDownloadController {

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(method = RequestMethod.GET)
    public void download(HttpServletResponse response, @RequestParam Long id) throws Exception {

        Attachment attachment = attachmentService.getAttachment(id);

        if (attachment == null) {
            return;
        }

        File file;
        file = new File(environmentService.getUploadPath() + attachment.getFilename());
        if (!file.exists() || file.isDirectory()) {
            return;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Length", file.length() + "");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String(attachment.getOriginalFilename().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
    }
}

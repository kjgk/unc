package com.unicorn.system.web.controller;

import com.unicorn.core.service.EnvironmentService;
import com.unicorn.utils.Identities;
import lombok.AllArgsConstructor;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.unicorn.base.web.ApiNamespace.API_V1;

@RestController
@RequestMapping(API_V1 + "/system/file/upload")
@AllArgsConstructor
public class FileUploadController {

    private EnvironmentService environmentService;

    @PostMapping
    public Map upload(@RequestParam(value = "attachment") MultipartFile attachment, HttpServletRequest request) throws Exception {

        String filename = attachment.getOriginalFilename();
        String tempFilename = Identities.randomLong() + "";
        String tempDirectory = environmentService.getTempPath();
        FileCopyUtils.copy(attachment.getInputStream(), new FileOutputStream(new File(tempDirectory + "/" + tempFilename)));

        boolean isImage = false;
        if (filename != null && (filename.toLowerCase().endsWith(".jpg")
                || filename.toLowerCase().endsWith(".jpeg")
                || filename.toLowerCase().endsWith(".png")
                || filename.toLowerCase().endsWith(".bmp"))) {
            isImage = true;
        }

        Map data = new HashMap();
        data.put("filename", filename);
        data.put("tempFilename", tempFilename);
        data.put("link", request.getContextPath() + API_V1 + "/system/file/download?tempFilename=" + tempFilename + (isImage ? "&type=image" : ""));
        return data;
    }
}

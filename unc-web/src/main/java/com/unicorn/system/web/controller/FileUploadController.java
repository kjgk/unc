package com.unicorn.system.web.controller;

import com.unicorn.system.service.EnvironmentService;
import com.unicorn.utils.Identities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/system/file/upload")
public class FileUploadController extends BaseController {

    @Autowired
    private EnvironmentService environmentService;

    @RequestMapping(method = RequestMethod.POST)
    public Map upload(@RequestParam(value = "attachment") CommonsMultipartFile attachment, HttpServletRequest request) throws Exception {

        String filename = attachment.getFileItem().getName();
        String tempFilename = Identities.randomLong() + "";

        String tempDirectory = environmentService.getTempPath();

        attachment.getFileItem().write(new File(tempDirectory + "/" + tempFilename));

        Map data = new HashMap();
        data.put("filename", filename);
        data.put("tempFilename", tempFilename);
        data.put("link", request.getContextPath() + "/system/file/download?tempFilename=" + tempFilename + "&type=image");
        return data;
    }

}

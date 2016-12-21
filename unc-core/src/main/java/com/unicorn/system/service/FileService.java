package com.unicorn.system.service;

import com.unicorn.core.exception.ServiceException;
import com.unicorn.utils.Identities;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileService {


    @Autowired
    private EnvironmentService environmentService;


    public String saveAttachmentFile(String tempFilename, String parentDirectory) throws ServiceException {

        String distPath = "/" + parentDirectory + "/" + new SimpleDateFormat("yyyy/MM").format(new Date());

        File distFile = new File(environmentService.getUploadPath() + distPath);
        if (!distFile.exists()) {
            try {
                FileUtils.forceMkdir(distFile);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServiceException(e);
            }
        }

        String distFilename = distPath + "/" + Identities.randomBase62(12);

        File in = new File(environmentService.getTempPath() + "/" + tempFilename);
        File out = new File(environmentService.getUploadPath() + "/" + distFilename);
        try {
            FileCopyUtils.copy(in, out);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return distFilename;
    }
}

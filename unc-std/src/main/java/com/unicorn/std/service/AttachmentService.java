package com.unicorn.std.service;

import com.unicorn.std.domain.po.Attachment;
import com.unicorn.std.repository.AttachmentRepository;
import com.unicorn.core.service.EnvironmentService;
import com.unicorn.utils.FileTypeUtils;
import com.unicorn.utils.SnowflakeIdWorker;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

@Service
@Transactional
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    public Attachment getAttachment(Long objectId) {

        return attachmentRepository.get(objectId);
    }

    public Attachment saveAttachment(String path, Attachment attachment) {

        String fileId = String.valueOf(snowflakeIdWorker.nextId());
        String filename = (StringUtils.isEmpty(path) ? "" : path) + "/" + fileId;
        File file = new File(environmentService.getTempPath() + "/" + attachment.getAttachmentInfo().getTempFilename());
        try {
            FileUtils.copyFile(file, new File(environmentService.getUploadPath() + filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        attachment.setFileSize(file.length());
        attachment.setFilename(filename);
        attachment.setFileType(FileTypeUtils.getFileType(file));
        attachment.setOriginalFilename(attachment.getAttachmentInfo().getFilename());
        return attachmentRepository.save(attachment);
    }

    public void deleteAttachment(Long objectId) {

        Attachment attachment = attachmentRepository.get(objectId);
        attachmentRepository.delete(attachment);
        try {
            FileUtils.forceDelete(new File(environmentService.getUploadPath() + attachment.getFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.unicorn.std.service;

import com.unicorn.core.service.EnvironmentService;
import com.unicorn.std.domain.po.Attachment;
import com.unicorn.std.repository.AttachmentRepository;
import com.unicorn.utils.FileTypeUtils;
import com.unicorn.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;

@Service
@Transactional
@Slf4j
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
        File file = new File(environmentService.getTempPath() + "/" + attachment.getFileInfo().getTempFilename());
        try {
            FileCopyUtils.copy(file, new File(environmentService.getUploadPath() + filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        attachment.setFileSize(file.length());
        attachment.setFilename(filename);
        attachment.setFileType(FileTypeUtils.getImageFileType(file));
        attachment.setOriginalFilename(attachment.getFileInfo().getFilename());
        return attachmentRepository.save(attachment);
    }

    public void deleteAttachment(Long objectId) {

        Attachment attachment = attachmentRepository.get(objectId);
        attachmentRepository.delete(attachment);
        boolean delete = new File(environmentService.getUploadPath() + attachment.getFilename()).delete();
        if (delete) {
            log.info("附件已删除");
        }
    }
}

package com.unicorn.std.service;

import com.unicorn.core.domain.vo.AttachmentInfo;
import com.unicorn.core.domain.vo.FileDownloadInfo;
import com.unicorn.std.domain.po.Attachment;
import com.unicorn.std.domain.po.ContentAttachment;
import com.unicorn.std.repository.ContentAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContentAttachmentService {

    @Autowired
    private ContentAttachmentRepository contentAttachmentRepository;

    @Autowired
    private AttachmentService attachmentService;

    public ContentAttachment getContentAttachment(Long objectId) {

        return contentAttachmentRepository.get(objectId);
    }

    public void save(ContentAttachment contentAttachment) {

        Assert.notNull(contentAttachment, "contentAttachment不能为空！");

        save(contentAttachment.getRelatedType(), contentAttachment.getRelatedId(), contentAttachment.getCategory(), Arrays.asList(contentAttachment));
    }

    public void save(String relatedType, Long relatedId, String category, List<ContentAttachment> list) {

        if (category == null) {
            category = "default";
        }

        Assert.notNull(relatedType, "relatedType不能为空！");
        Assert.notNull(relatedId, "relatedId不能为空！");

        // 删除附件
        List<ContentAttachment> currentList = contentAttachmentRepository.getAttachmentList(relatedId, category);
        List<Long> deletedList = new ArrayList();
        if (!CollectionUtils.isEmpty(currentList)) {
            for (ContentAttachment currentAttachment : currentList) {
                boolean delete = true;
                for (ContentAttachment contentAttachment : list) {
                    if (contentAttachment.getAttachment() != null
                            && currentAttachment.getAttachment().getObjectId().equals(contentAttachment.getAttachment().getObjectId())) {
                        delete = false;
                        break;
                    }
                }
                if (delete) {
                    contentAttachmentRepository.delete(currentAttachment);
                    attachmentService.deleteAttachment(currentAttachment.getAttachment().getObjectId());
                    deletedList.add(currentAttachment.getObjectId());
                }
            }
        }

        // 更新排序号
        int orderNo = 1;
        for (ContentAttachment contentAttachment : currentList) {
            if (!deletedList.contains(contentAttachment.getObjectId())) {
                contentAttachment.setOrderNo(orderNo++);
            }
        }

        // 保存附件
        if (!CollectionUtils.isEmpty(list)) {
            for (ContentAttachment contentAttachment : list) {
                if (contentAttachment.getAttachment() == null) {
                    Attachment attachment = new Attachment();
                    attachment.setFileInfo(contentAttachment.getFileInfo());
                    String path = "/" + relatedType + "/" + category;
                    contentAttachment.setAttachment(attachmentService.saveAttachment(path, attachment));
                    contentAttachment.setOrderNo(orderNo++);
                    contentAttachment.setRelatedId(relatedId);
                    contentAttachment.setRelatedType(relatedType);
                    contentAttachment.setCategory(category);
                    contentAttachmentRepository.save(contentAttachment);
                }
            }
        }
    }

    public List<ContentAttachment> getAttachmentList(Long relatedId) {

        return contentAttachmentRepository.getAttachmentList(relatedId);
    }

    public List<ContentAttachment> getAttachmentList(Long relatedId, String category) {

        return contentAttachmentRepository.getAttachmentList(relatedId, category);
    }


    public AttachmentInfo getAttachmentInfo(Long relatedId) {

        List<ContentAttachment> attachmentList = getAttachmentList(relatedId);
        if (CollectionUtils.isEmpty(attachmentList)) {
            return null;
        }
        return buildAttachmentInfo(attachmentList.get(0).getAttachment());
    }

    public AttachmentInfo getAttachmentInfo(Long relatedId, String category) {

        List<ContentAttachment> attachmentList = getAttachmentList(relatedId, category);
        if (CollectionUtils.isEmpty(attachmentList)) {
            return null;
        }
        return buildAttachmentInfo(attachmentList.get(0).getAttachment());
    }

    public List<AttachmentInfo> getAttachmentInfos(Long relatedId) {

        return getAttachmentList(relatedId)
                .stream()
                .map(ContentAttachment::getAttachment)
                .map(this::buildAttachmentInfo)
                .collect(Collectors.toList());
    }

    public List<AttachmentInfo> getAttachmentInfos(Long relatedId, String category) {

        return getAttachmentList(relatedId, category)
                .stream()
                .map(ContentAttachment::getAttachment)
                .map(this::buildAttachmentInfo)
                .collect(Collectors.toList());
    }

    /**************************** 附件下载 ****************************/
    public FileDownloadInfo getAttachmentLink(Long relatedId) {

        List<ContentAttachment> attachmentList = getAttachmentList(relatedId);
        if (CollectionUtils.isEmpty(attachmentList)) {
            return null;
        }
        return buildFileDownloadInfo(attachmentList.get(0).getAttachment());
    }

    public FileDownloadInfo getAttachmentLink(Long relatedId, String category) {

        List<ContentAttachment> attachmentList = getAttachmentList(relatedId, category);
        if (CollectionUtils.isEmpty(attachmentList)) {
            return null;
        }
        return buildFileDownloadInfo(attachmentList.get(0).getAttachment());
    }

    public List<FileDownloadInfo> getAttachmentLinks(Long relatedId) {

        return getAttachmentList(relatedId)
                .stream()
                .map(ContentAttachment::getAttachment)
                .map(this::buildFileDownloadInfo)
                .collect(Collectors.toList());
    }

    public List<FileDownloadInfo> getAttachmentLinks(Long relatedId, String category) {

        return getAttachmentList(relatedId, category)
                .stream()
                .map(ContentAttachment::getAttachment)
                .map(this::buildFileDownloadInfo)
                .collect(Collectors.toList());
    }

    private FileDownloadInfo buildFileDownloadInfo(Attachment attachment) {

        return FileDownloadInfo.valueOf(buildAttachmentLink(attachment), attachment.getOriginalFilename());
    }

    private AttachmentInfo buildAttachmentInfo(Attachment attachment) {

        AttachmentInfo attachmentInfo = new AttachmentInfo();
        attachmentInfo.setUrl(buildAttachmentLink(attachment));
        if (attachment.getFileType() != null && "jpg,png,gif,tif,bmp".contains(attachment.getFileType())) {
            attachmentInfo.setImageUrl(buildImageAttachmentLink(attachment));
        }
        attachmentInfo.setAttachmentId(attachment.getObjectId());
        attachmentInfo.setFilename(attachment.getOriginalFilename());
        return attachmentInfo;

    }

    /**************************** 图片链接 ****************************/
    public String getImageAttachmentLink(Long relatedId) {

        List<ContentAttachment> attachmentList = getAttachmentList(relatedId);
        if (CollectionUtils.isEmpty(attachmentList)) {
            return null;
        }
        return buildImageAttachmentLink(attachmentList.get(0).getAttachment());
    }

    public String getImageAttachmentLink(Long relatedId, String category) {

        List<ContentAttachment> attachmentList = getAttachmentList(relatedId, category);
        if (CollectionUtils.isEmpty(attachmentList)) {
            return null;
        }
        return buildImageAttachmentLink(attachmentList.get(0).getAttachment());
    }

    public List<String> getImageAttachmentLinks(Long relatedId) {

        return getAttachmentList(relatedId)
                .stream()
                .map(ContentAttachment::getAttachment)
                .map(this::buildImageAttachmentLink)
                .collect(Collectors.toList());
    }

    public List<String> getImageAttachmentLinks(Long relatedId, String category) {

        return getAttachmentList(relatedId, category)
                .stream()
                .map(ContentAttachment::getAttachment)
                .map(this::buildImageAttachmentLink)
                .collect(Collectors.toList());
    }

    private String buildImageAttachmentLink(Attachment attachment) {

        return "/content/image/" + Base64Utils.encodeToString(attachment.getFilename().getBytes());
    }

    private String buildAttachmentLink(Attachment attachment) {

        return "/content/download?id=" + attachment.getObjectId();
    }
}

package com.unicorn.std.service;

import com.unicorn.core.domain.vo.AttachmentInfo;
import com.unicorn.std.domain.po.Attachment;
import com.unicorn.std.domain.po.ContentAttachment;
import com.unicorn.std.repository.ContentAttachmentRepository;
import com.unicorn.utils.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ContentAttachmentService {

    private ContentAttachmentRepository contentAttachmentRepository;

    private AttachmentService attachmentService;

    public void save(String relatedType, Long relatedId, String category, AttachmentInfo... attachments) {

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
                for (AttachmentInfo attachment : attachments) {
                    if (attachment.getAttachmentId() != null && currentAttachment.getAttachment().getObjectId().equals(attachment.getAttachmentId())) {
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
        if (attachments != null && attachments.length > 0) {
            String path = "/" + relatedType + "/" + category + "/" + DateUtils.format(new Date(), "yyyyMM");
            for (AttachmentInfo attachmentInfo : attachments) {
                if (attachmentInfo.getAttachmentId() == null) {
                    Attachment attachment = new Attachment();
                    ContentAttachment contentAttachment = new ContentAttachment();
                    attachment.setAttachmentInfo(attachmentInfo);
                    contentAttachment.setAttachment(attachmentService.saveAttachment(path, attachment));
                    contentAttachment.setRelatedId(relatedId);
                    contentAttachment.setRelatedType(relatedType);
                    contentAttachment.setCategory(category);
                    contentAttachment.setOrderNo(orderNo++);
                    contentAttachmentRepository.save(contentAttachment);
                }
            }
        }
    }

    public ContentAttachment getContentAttachment(Long objectId) {

        return contentAttachmentRepository.get(objectId);
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

    public List<AttachmentInfo> getAttachmentInfoList(Long relatedId) {

        return getAttachmentList(relatedId)
                .stream()
                .map(ContentAttachment::getAttachment)
                .map(this::buildAttachmentInfo)
                .collect(Collectors.toList());
    }

    public List<AttachmentInfo> getAttachmentInfoList(Long relatedId, String category) {

        return getAttachmentList(relatedId, category)
                .stream()
                .map(ContentAttachment::getAttachment)
                .map(this::buildAttachmentInfo)
                .collect(Collectors.toList());
    }

    /*  */

    /**************************** 附件下载 ****************************//*
    public AttachmentInfo getAttachmentLink(Long relatedId) {

        List<ContentAttachment> attachmentList = getAttachmentList(relatedId);
        if (CollectionUtils.isEmpty(attachmentList)) {
            return null;
        }
        return buildAttachmentInfo(attachmentList.get(0).getAttachment());
    }

    public AttachmentInfo getAttachmentLink(Long relatedId, String category) {

        List<ContentAttachment> attachmentList = getAttachmentList(relatedId, category);
        if (CollectionUtils.isEmpty(attachmentList)) {
            return null;
        }
        return buildAttachmentInfo(attachmentList.get(0).getAttachment());
    }

    public List<AttachmentInfo> getAttachmentLinks(Long relatedId) {

        return getAttachmentList(relatedId)
                .stream()
                .map(ContentAttachment::getAttachment)
                .map(this::buildAttachmentInfo)
                .collect(Collectors.toList());
    }

    public List<AttachmentInfo> getAttachmentLinks(Long relatedId, String category) {

        return getAttachmentList(relatedId, category)
                .stream()
                .map(ContentAttachment::getAttachment)
                .map(this::buildAttachmentInfo)
                .collect(Collectors.toList());
    }*/
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

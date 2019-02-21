package com.unicorn.std.repository;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.std.domain.po.ContentAttachment;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentAttachmentRepository extends BaseRepository<ContentAttachment> {

    @Query(value = "select a from ContentAttachment a where a.relatedId = ?1 order by a.orderNo")
    List<ContentAttachment> getAttachmentList(Long relatedId);

    @Query(value = "select a from ContentAttachment a where a.relatedId = ?1 and a.category = ?2 order by a.orderNo")
    List<ContentAttachment> getAttachmentList(Long relatedId, String category);
}

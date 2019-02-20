package com.unicorn.common.domain;

import com.unicorn.common.domain.po.User;

import java.util.Date;

public interface Loggable {

    Date getCreatedDate();

    void setCreatedDate(Date creationDate);

    Date getLastUpdateDate();

    void setLastUpdateDate(Date lastUpdateDate);

    User getCreatedBy();

    void setCreatedBy(User createdBy);

    User getLastUpdatedBy();

    void setLastUpdatedBy(User lastUpdatedBy);
}

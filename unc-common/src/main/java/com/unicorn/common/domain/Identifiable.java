package com.unicorn.common.domain;

import java.io.Serializable;

public interface Identifiable extends Serializable {

    Long getObjectId();

    void setObjectId(Long objectId);
}

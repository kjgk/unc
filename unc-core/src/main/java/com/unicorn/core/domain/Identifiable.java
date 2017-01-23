package com.unicorn.core.domain;

import java.io.Serializable;

public interface Identifiable extends Serializable {

    String getObjectId();

    void setObjectId(String objectId);
}

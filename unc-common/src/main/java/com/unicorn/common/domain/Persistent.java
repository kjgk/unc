package com.unicorn.common.domain;

public interface Persistent extends Identifiable {

    Integer getDeleted();

    void setDeleted(Integer deleted);
}

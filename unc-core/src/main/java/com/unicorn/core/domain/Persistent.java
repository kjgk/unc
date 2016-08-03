package com.unicorn.core.domain;

public interface Persistent extends Identifiable {

    Integer getDeleted();

    void setDeleted(Integer deleted);
}

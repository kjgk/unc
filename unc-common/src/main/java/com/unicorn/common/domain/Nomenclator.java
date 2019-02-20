package com.unicorn.common.domain;

public interface Nomenclator extends Persistent {

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

}
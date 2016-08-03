package com.unicorn.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class DefaultPersistent extends DefaultLoggable implements Persistent {

    @JsonIgnore
    @Column(name = "DELETED")
    private Integer deleted;

    protected DefaultPersistent() {

        this.deleted = 0;
    }
}
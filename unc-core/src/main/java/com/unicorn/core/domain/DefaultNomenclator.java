package com.unicorn.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class DefaultNomenclator extends DefaultPersistent implements Nomenclator {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Override
    public String toString() {
        return "DefaultNomenclator [name=" + name + ", description="
                + description + "]";
    }


}

package com.unicorn.common.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class DefaultNomenclator extends DefaultPersistent implements Nomenclator {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Override
    public String toString() {
        return "DefaultNomenclator [name=" + name + ", description="
                + description + "]";
    }


}

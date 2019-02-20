package com.unicorn.common.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class DefaultIdentifiable implements Identifiable {

    @Id
    @Column(name = "objectid")
    private Long objectId;

}

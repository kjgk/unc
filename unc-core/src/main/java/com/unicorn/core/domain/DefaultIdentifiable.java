package com.unicorn.core.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class DefaultIdentifiable implements Identifiable {

    @Id
    @Column(name = "OBJECTID")
    private String objectId;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false)
    private Long id;
}

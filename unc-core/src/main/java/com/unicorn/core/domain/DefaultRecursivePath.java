package com.unicorn.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@Getter
@Setter
@MappedSuperclass
public abstract class DefaultRecursivePath<T> {

    @OneToOne
    @JoinColumn(name = "genuine_id")
    @JsonIgnore
    private T genuine;

    @Id
    private String path;

    private Integer depth;
}

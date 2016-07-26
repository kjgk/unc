package com.unicorn.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public abstract class DefaultRecursive<T> extends DefaultNomenclator implements Recursive<T> {

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private T parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @OrderBy(value = "ORDER_NO asc")
    @Where(clause = "deleted=0")
    @JsonIgnore
    private List<T> childList;

    private Integer orderNo;
}

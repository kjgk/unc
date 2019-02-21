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
public abstract class DefaultRecursive<T extends DefaultRecursive> extends DefaultNomenclator implements Recursive<T> {

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @JsonIgnore
    private T parent;

    @OneToMany(mappedBy = "parentId", fetch = FetchType.LAZY)
    @OrderBy(value = "order_no asc")
    @Where(clause = "deleted=0")
    @JsonIgnore
    private List<T> childList;

    private Integer orderNo;
}

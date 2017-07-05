package com.unicorn.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.unicorn.system.domain.po.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class DefaultLoggable extends DefaultIdentifiable implements Loggable, Identifiable {

    @JsonIgnore
    @CreatedBy
    @OneToOne
    @JoinColumn(name = "CREATED_BY", nullable = true)
    @Fetch(FetchMode.JOIN)
    private User createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @JsonIgnore
    @LastModifiedBy
    @OneToOne
    @JoinColumn(name = "LAST_UPDATED_BY", nullable = true)
    @Fetch(FetchMode.JOIN)
    private User lastUpdatedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATE_DATE", nullable = true)
    private Date lastUpdateDate;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (getObjectId() == null ? 0 : getObjectId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DefaultLoggable other = (DefaultLoggable) obj;
        if (getObjectId() == null) {
            if (other.getObjectId() != null) {
                return false;
            }
        } else if (!getObjectId().equals(other.getObjectId())) {
            return false;
        }
        return true;
    }
}
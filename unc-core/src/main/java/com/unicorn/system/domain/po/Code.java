package com.unicorn.system.domain.po;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.unicorn.core.domain.DefaultRecursive;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "SYS_CODE")
@EntityListeners({AuditingEntityListener.class})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Code extends DefaultRecursive<Code> {

    private String tag;

}

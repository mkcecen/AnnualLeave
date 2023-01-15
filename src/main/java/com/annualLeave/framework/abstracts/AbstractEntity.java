package com.annualLeave.framework.abstracts;

import com.annualLeave.framework.generic.GenericEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity implements GenericEntity {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime createDate = LocalDateTime.now();

    @Override
    public AbstractEntity clone() throws CloneNotSupportedException {
        return (AbstractEntity) super.clone();
    }
}

package com.annualLeave.framework.generic;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface GenericEntity extends Serializable, Cloneable {

    Long getId();
    void setId(Long id);

    LocalDateTime getCreateDate();
    void setCreateDate(LocalDateTime createDate);

    public GenericEntity clone() throws CloneNotSupportedException;
}

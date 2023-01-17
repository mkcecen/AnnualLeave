package com.annualLeave.entity;

import com.annualLeave.framework.abstracts.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Login extends AbstractEntity {

    private LocalDate loginDate = LocalDate.now();
    private String token = null;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

}
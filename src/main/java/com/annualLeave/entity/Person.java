package com.annualLeave.entity;

import com.annualLeave.enums.*;
import com.annualLeave.framework.abstracts.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Person extends AbstractEntity {

    private String name;
    private String surname;
    private String email;
    private String password;

    private LocalDate birthDate;
    private LocalDate startWorkDay = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.ACTIVE;

    @Enumerated(EnumType.STRING)
    private Language language = Language.TR;

    @Enumerated(EnumType.STRING)
    private GenderType genderType = GenderType.NONE;

    @Enumerated(EnumType.STRING)
    private Department department = Department.NONE;

    @Enumerated(EnumType.STRING)
    private PersonType personType = PersonType.OTHER;

    @Formula("(concat(name, ' ', surname))")
    private String fullName;

}

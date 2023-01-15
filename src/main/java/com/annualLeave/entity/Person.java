package com.annualLeave.entity;

import com.annualLeave.enums.Department;
import com.annualLeave.enums.GenderType;
import com.annualLeave.enums.Language;
import com.annualLeave.enums.StatusType;
import com.annualLeave.framework.abstracts.AbstractEntity;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Person extends AbstractEntity {

    private String name;
    private String surname;
    private String email;
    private Boolean isManager = Boolean.FALSE;

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

    @Formula("(concat(name, ' ', surname))")
    private String fullName;

    @NotAudited
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<AnnualPermit> annualPermits;
}

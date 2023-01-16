package com.annualLeave.entity;

import com.annualLeave.enums.PermitStatus;
import com.annualLeave.enums.PermitType;
import com.annualLeave.framework.abstracts.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class AnnualPermit extends AbstractEntity {

    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = LocalDate.now().plusDays(15);
    private Integer permitDay = 0;

    @Enumerated(EnumType.STRING)
    private PermitStatus status = PermitStatus.WAITING;

    @Enumerated(EnumType.STRING)
    private PermitType type = PermitType.YEARLY;

    @NotAudited
    @OneToOne(fetch = FetchType.LAZY)
    private Person person;

    @NotAudited
    @OneToOne(fetch = FetchType.LAZY)
    private Person approvingPerson;
}

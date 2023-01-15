package com.annualLeave.entity;

import com.annualLeave.enums.StatusType;
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

    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.ACTIVE;

    @NotAudited
    @OneToOne(fetch = FetchType.LAZY)
    private Person person;
}

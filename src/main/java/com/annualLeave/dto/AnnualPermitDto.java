package com.annualLeave.dto;


import com.annualLeave.enums.*;
import com.annualLeave.framework.abstracts.AbstractDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
@ApiModel(value = "Annual Permit", description = "Annual Permit Model")
public class AnnualPermitDto extends AbstractDto {
    private static final long serialVersionUID = 1L;

    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = LocalDate.now().plusDays(15);
    private Integer permitDay = 0;

    private PermitStatus status = PermitStatus.WAITING;
    private PermitType type = PermitType.YEARLY;
    private PersonDto person = null;
    private PersonDto approvingPerson = null;
}

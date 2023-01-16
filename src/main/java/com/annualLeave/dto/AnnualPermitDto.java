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

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer permitDay;

    private PermitStatus status;
    private PermitType type;
    private PersonDto person;
    private PersonDto approvingPerson;
}

package com.annualLeave.dto;


import com.annualLeave.enums.Department;
import com.annualLeave.enums.GenderType;
import com.annualLeave.enums.Language;
import com.annualLeave.enums.StatusType;
import com.annualLeave.framework.abstracts.AbstractDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
@ApiModel(value = "Person", description = "Person Model")
public class PersonDto extends AbstractDto {
    private static final long serialVersionUID = 1L;

    private String name;
    private String surname;
    private String email;
    private String fullName;
    private Boolean isManager;

    private LocalDate birthDate;
    private LocalDate startWorkDay;

    private StatusType status;
    private Language language;
    private GenderType genderType;
    private Department department;
}

package com.annualLeave.dto;

import com.annualLeave.framework.abstracts.AbstractDto;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "Login", description = "Login Model")
public class LoginDto extends AbstractDto {
    private static final long serialVersionUID = 1L;

    private String token;
    private PersonDto person;

}

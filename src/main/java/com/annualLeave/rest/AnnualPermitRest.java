package com.annualLeave.rest;

import com.annualLeave.dto.AnnualPermitDto;
import com.annualLeave.entity.AnnualPermit;
import com.annualLeave.framework.abstracts.AbstractRest;
import com.annualLeave.mapper.AnnualPermitMapper;
import com.annualLeave.service.AnnualPermitService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/annualPermit")
@Api(value = "AnnualPermit Api documentation")
public class AnnualPermitRest extends AbstractRest<AnnualPermit, AnnualPermitDto> {

    private AnnualPermitService service;
    private AnnualPermitMapper mapper;

    @Autowired
    public AnnualPermitRest(@Lazy AnnualPermitService service, AnnualPermitMapper mapper) {
        super(service, mapper);
        this.service = service;
        this.mapper = mapper;
    }

}

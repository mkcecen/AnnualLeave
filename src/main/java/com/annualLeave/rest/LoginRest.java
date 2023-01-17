package com.annualLeave.rest;

import com.annualLeave.dto.LoginDto;
import com.annualLeave.entity.Login;
import com.annualLeave.framework.abstracts.AbstractRest;
import com.annualLeave.mapper.LoginMapper;
import com.annualLeave.mapper.PersonMapper;
import com.annualLeave.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Api(value = "Login Api documentation")
public class LoginRest extends AbstractRest<Login, LoginDto> {

    private LoginMapper mapper;
    private LoginService service;
    private PersonMapper personMapper;

    @Autowired
    public LoginRest(@Lazy LoginService service, LoginMapper mapper, PersonMapper personMapper) {
        super(service, mapper);
        this.personMapper = personMapper;
        this.mapper = mapper;
        this.service = service;
    }


    @GetMapping("/login")
    @ApiOperation(value = "Login request")
    public ResponseEntity<LoginDto> login(@RequestParam String email, @RequestParam String password, @RequestParam String language) throws Exception {
        Login login = service.login(email, password,language);
        return new ResponseEntity<LoginDto>(mapper.map(login), HttpStatus.OK);
    }

}

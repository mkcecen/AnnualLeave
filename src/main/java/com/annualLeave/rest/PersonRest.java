package com.annualLeave.rest;

import com.annualLeave.dto.PersonDto;
import com.annualLeave.entity.Person;
import com.annualLeave.framework.abstracts.AbstractRest;
import com.annualLeave.mapper.PersonMapper;
import com.annualLeave.service.PersonService;
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

import java.util.List;

@RestController
@RequestMapping("/person")
@Api(value = "Person Api documentation")
public class PersonRest extends AbstractRest<Person, PersonDto> {

    private PersonService service;
    private PersonMapper mapper;

    @Autowired
    public PersonRest(@Lazy PersonService service, PersonMapper mapper) {
        super(service, mapper);
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/getAllPersons")
    @ApiOperation(value = "Get All Persons")
    public ResponseEntity<List<PersonDto>> getAllPersons() throws Exception {
        List<Person> users = service.getAllPersons();
        return new ResponseEntity<List<PersonDto>>(mapper.mapAll(users), HttpStatus.OK);
    }

    @GetMapping("/findByName")
    @ApiOperation(value = "Person Search By Name")
    public ResponseEntity<List<PersonDto>> findByName(@RequestParam("name") String name) throws Exception {
        List<Person> users = service.findByName(name);
        return new ResponseEntity<List<PersonDto>>(mapper.mapAll(users), HttpStatus.OK);
    }
}

package com.annualLeave.mapper;


import com.annualLeave.dto.PersonDto;
import com.annualLeave.entity.Person;
import com.annualLeave.framework.abstracts.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper extends AbstractMapper<Person, PersonDto> {

    @Override
    public PersonDto map(Person entity) {
        PersonDto dto = super.map(entity);
        dto.setPassword(null);
        return dto;
    }
}

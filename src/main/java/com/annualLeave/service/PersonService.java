package com.annualLeave.service;

import com.annualLeave.dao.PersonDao;
import com.annualLeave.entity.Person;
import com.annualLeave.framework.abstracts.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonService extends AbstractService<Person> {

    private PersonDao dao;

    private BCryptPasswordEncoder encoder;

    @Autowired
    public PersonService(@Lazy PersonDao dao) {
        super();
        this.dao = dao;
    }

    @Override
    public Person save(Person entity) throws Exception {

        return super.save(entity);
    }


    public List<Person> getAllPersons() throws Exception {
        return dao.findAll();
    }

    public List<Person> findByName(String name) {
        return dao.findByName(name);
    }
}

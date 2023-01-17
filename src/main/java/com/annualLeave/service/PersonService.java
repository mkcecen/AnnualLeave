package com.annualLeave.service;

import com.annualLeave.dao.PersonDao;
import com.annualLeave.entity.Person;
import com.annualLeave.enums.Language;
import com.annualLeave.framework.abstracts.AbstractService;
import com.annualLeave.framework.exceptions.AuthenticateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class PersonService extends AbstractService<Person> {
    private PersonDao dao;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public PersonService(@Lazy PersonDao dao, @Lazy BCryptPasswordEncoder encoder) {
        super();
        this.dao = dao;
        this.encoder = encoder;
    }

    @Override
    public Person save(Person entity) throws Exception {
        Person oldPerson = null;
        if (entity.getId() == null) {
            entity.setCreateDate(LocalDateTime.now());
        }else{
            oldPerson = dao.getById(entity.getId());
        }
        if (entity.getLanguage() == null) {
            createRuntimeException("PRSN.0001", "Dil boş geçilemez!", null);
        }
        if (entity.getEmail() == null) {
            createRuntimeException("PRSN.0002", "E-Mail boş geçilemez!", null);
        }
        if (entity.getPassword() == null) {
            createRuntimeException("PRSN.0003", "Parola boş geçilemez!", null);
        }
        if (empty(entity.getName()) || empty(entity.getSurname())) {
            createRuntimeException("PRSN.0004", "Ad-Soyad alanı boş geçilemez!", null);
        }

        if (!empty(entity.getPassword())) {//Password boş değilse değiştir.
            entity.setPassword(encoder.encode(entity.getPassword()));
        } else if (oldPerson != null) {//TODO : password null gelirse iceridekini de siliyor o yuzden eskisini ekliyoruz
            entity.setPassword(oldPerson.getPassword());
        }
        return super.save(entity);
    }

    public List<Person> getAllPersons() throws Exception {
        return dao.findAll();
    }

    public List<Person> findByName(String name) {
        return dao.findByName(name);
    }

    public Person findFirstByEmailAndPassword(String email, String password, String language) throws Exception {
        Person person = dao.findFirstByEmail(email);
        if (person != null) {
            if (!encoder.matches(password, person.getPassword())) {
                if(Language.TR.getValue().equals(language)){
                    throw new AuthenticateException("E-Mail veya Şifre Hatalı!");
                }else{
                    throw new AuthenticateException("Invalid email or password");
                }
            }
        }
        return person;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoders() {
        return new BCryptPasswordEncoder();
    }
}

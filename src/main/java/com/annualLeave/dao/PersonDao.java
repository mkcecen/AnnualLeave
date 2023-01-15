package com.annualLeave.dao;

import com.annualLeave.entity.Person;
import com.annualLeave.framework.generic.GenericDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonDao extends GenericDao<Person> {

    public Person findFirstByEmail(String email);

    @Query("FROM Person U WHERE  (TRANSLATE_UPPER_TR(U.fullName)) LIKE CONCAT((TRANSLATE_UPPER_TR(?1)),'%') ")
    List<Person> findByName(String name);

}

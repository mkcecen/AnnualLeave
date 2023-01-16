package com.annualLeave.dao;

import com.annualLeave.entity.AnnualPermit;
import com.annualLeave.entity.Person;
import com.annualLeave.enums.PermitType;
import com.annualLeave.framework.generic.GenericDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AnnualPermitDao extends GenericDao<AnnualPermit> {

    @Query("select coalesce( sum(permitDay),0) from AnnualPermit where person=?1 and type=?2")
    Integer calculateTotalPermit(Person person, PermitType type);
}

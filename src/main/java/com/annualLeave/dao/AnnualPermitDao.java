package com.annualLeave.dao;

import com.annualLeave.entity.AnnualPermit;
import com.annualLeave.entity.Person;
import com.annualLeave.enums.PermitType;
import com.annualLeave.framework.generic.GenericDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface AnnualPermitDao extends GenericDao<AnnualPermit> {

    @Query("select coalesce( sum(permitDay),0) from AnnualPermit where person=?1 and type=?2")
    Integer calculateTotalPermit(Person person, PermitType type);

    @Query("SELECT MAX(AP) FROM AnnualPermit AP where ( ?1 BETWEEN  AP.startDate AND AP.endDate ) or (?2 BETWEEN AP.startDate AND AP.endDate) ")
    AnnualPermit checkAnnualPermit(LocalDate startDate, LocalDate endDate);
}

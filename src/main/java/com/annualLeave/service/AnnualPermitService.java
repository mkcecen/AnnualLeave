package com.annualLeave.service;

import com.annualLeave.dao.AnnualPermitDao;
import com.annualLeave.entity.AnnualPermit;
import com.annualLeave.entity.Person;
import com.annualLeave.enums.PermitStatus;
import com.annualLeave.enums.PermitType;
import com.annualLeave.enums.PersonType;
import com.annualLeave.framework.abstracts.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Service
public class AnnualPermitService extends AbstractService<AnnualPermit> {

    private AnnualPermitDao dao;
    private PersonService personService;

    @Autowired
    public AnnualPermitService(@Lazy AnnualPermitDao dao,PersonService personService) {
        super();
        this.dao = dao;
        this.personService = personService;
    }

    @Override
    public AnnualPermit save(AnnualPermit entity) throws Exception {
        if (entity.getPerson() == null) {
            throw new Exception("İzin talebi yapan personel boş olamaz!");
        }
        if (entity.getStartDate().isAfter(entity.getEndDate())) {
            throw new Exception("Başlangıç tarihi bitiş tarihinden sonra olamaz!");
        }

        Person person = personService.getById(entity.getPerson().getId());
        Period period = Period.between(person.getStartWorkDay(), LocalDate.now());
        Integer day = calculateDays(entity.getStartDate(), entity.getEndDate());
        entity.setPermitDay(day);


        if (PermitType.YEARLY.equals(entity.getType()) && period.getYears() < 1) {
            throw new Exception("Yıllık ücretli izne hak kazanmak için en az bir yıl çalışmış olmak gerekmektedir!");
        } else if (PermitType.EXCUSE.equals(entity.getType())) {
            if (period.getYears() > 0) {
                throw new Exception("Mazeret izni sadece 1.yılını doldurmayan çalışanlar için geçerlidir! ");
            } else if (day > 5) {
                throw new Exception("5 iş gününden fazla mazeret izni kullanılamaz!");
            }
        }

        if (!PermitStatus.WAITING.equals(entity.getStatus())) {
            if (entity.getApprovingPerson() == null) {
                throw new Exception("İzin onaylamak/reddeden personel girilmeli!");
            } else if (!PersonType.ADMIN.equals(entity.getPerson().getPersonType())) {
                throw new Exception("İzin onaylamak/reddeden personel yönetici olmalı!");
            } else if (entity.getApprovingPerson() == null) {
                throw new Exception("Durum değişitirmek için onaylayan kullanıcının seçilmesi gerekmektedir!");
            }
        }

        entity = super.save(entity);
        calculateTotalPermit(entity.getPerson(), PermitType.YEARLY, period);
        return entity;
    }

    public Integer calculateDays(LocalDate startDate, LocalDate endDate) {
        Integer day = 0;
        long dats = ChronoUnit.DAYS.between(startDate, endDate);
        for (int i = 0; i <= dats; i++) {
            if (!(startDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    startDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++day;
            }
            startDate = startDate.plusDays(1);
        }
        return day;
    }

    private void calculateTotalPermit(Person person, PermitType type, Period period) throws Exception {
        int totalYear = period.getYears();
        Integer totalPermit = 0;
        if (totalYear <= 5) {
            totalPermit = totalYear * 15;
            if (totalPermit < dao.calculateTotalPermit(person, type)) {
                throw new Exception("İzin hakkınız bulunmamaktadır!");
            }
        } else if (totalYear > 5 && totalYear <= 10) {
            //TODO: 5*15
            totalPermit = 75;
            totalPermit += (totalYear - 5) * 18;
            if (totalPermit < dao.calculateTotalPermit(person, type)) {
                throw new Exception("İzin hakkınız bulunmamaktadır!");
            }
        } else {
            //TODO: (5*15) + (5*18)
            totalPermit = 1555;
            totalPermit += (totalYear - 10) * 24;
            if (totalPermit < dao.calculateTotalPermit(person, type)) {
                throw new Exception("İzin hakkınız bulunmamaktadır!");
            }
        }

    }
}

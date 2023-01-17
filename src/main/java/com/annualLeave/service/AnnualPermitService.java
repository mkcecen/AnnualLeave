package com.annualLeave.service;

import com.annualLeave.dao.AnnualPermitDao;
import com.annualLeave.entity.AnnualPermit;
import com.annualLeave.entity.Person;
import com.annualLeave.enums.PermitStatus;
import com.annualLeave.enums.PermitType;
import com.annualLeave.enums.PersonType;
import com.annualLeave.framework.abstracts.AbstractService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.Key;
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
        if(isFalse(hasSession())){
            createRuntimeException("ANP.0001", "Login olmanız gerekmektedir!", null);
        }
        if(entity.getPerson()==null){
            createRuntimeException("ANP.0002", "İzin talep eden personel zorunludur!", null);
        }

        if (entity.getStartDate() == null || entity.getEndDate() == null) {
            createRuntimeException("ANP.0003", "İzin başlangıç/bitiş tarihi zorunludur!", null);
        }
        if (dao.checkAnnualPermit(entity.getStartDate(), entity.getEndDate()) != null) {
            createRuntimeException("ANP.0004", "Başlangıç/bitiş tarihleri arasında izin talebiniz mevcut!", null);
        }
        if (!PermitStatus.WAITING.equals(entity.getStatus())) {
            entity.setApprovingPerson(session.getPerson());
            if (!PersonType.ADMIN.equals(entity.getPerson().getPersonType())) {
                createRuntimeException("ANP.0005", "İzin onaylamak/reddeden personel yönetici olmalı!", null);
                throw new Exception("");
            }else if(entity.getPerson().getId().equals(entity.getApprovingPerson().getId())){
                createRuntimeException("ANP.0006", "İzin talep eden ile onaylayan aynı kullanıcı olamaz!", null);
            }
        }

        Person person = personService.getById(entity.getPerson().getId());
        Period period = Period.between(person.getStartWorkDay(), LocalDate.now());

        Integer day = calculateDays(entity.getStartDate(), entity.getEndDate());
        entity.setPermitDay(day);


        if (PermitType.YEARLY.equals(entity.getType()) && period.getYears() < 1) {
            createRuntimeException("ANP.0007", "Yıllık ücretli izne hak kazanmak için en az bir yıl çalışmış olmak gerekmektedir!", null);
        } else if (PermitType.EXCUSE.equals(entity.getType())) {
            if (period.getYears() > 0) {
                createRuntimeException("ANP.0008", "Mazeret izni sadece 1.yılını doldurmayan çalışanlar için geçerlidir!", null);
            } else if (day > 5) {
                createRuntimeException("ANP.0009", "5 iş gününden fazla mazeret izni kullanılamaz!", null);
            }
        }



        entity = super.save(entity);
        calculateTotalPermit(entity.getPerson(), PermitType.YEARLY, period);
        return entity;
    }

    public Integer calculateDays(LocalDate startDate, LocalDate endDate) throws Exception{
        Integer day = 0;
        long dats = ChronoUnit.DAYS.between(startDate, endDate);
        JsonArray jsonArray = checkOfficialHolidays(startDate);
        for (int i = 0; i <= dats; i++) {
            Boolean isRepublcHoliday = Boolean.FALSE;
            if (!(startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                for (JsonElement packageListElement : jsonArray) {
                    JsonObject packageListObj = packageListElement.getAsJsonObject();
                    String date = packageListObj.get("start").getAsJsonObject().get("date").getAsString();

                    if (startDate.toString().equals(date)) {
                        isRepublcHoliday = true;
                        break;
                    }
                }
                if (!isRepublcHoliday) {
                    ++day;
                }
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
                createRuntimeException("ANP.0010", "İzin hakkınız bulunmamaktadır!", null);
            }
        } else if (totalYear > 5 && totalYear <= 10) {
            //TODO: 5*15
            totalPermit = 75;
            totalPermit += (totalYear - 5) * 18;
            if (totalPermit < dao.calculateTotalPermit(person, type)) {
                createRuntimeException("ANP.0010", "İzin hakkınız bulunmamaktadır!", null);
            }
        } else {
            //TODO: (5*15) + (5*18)
            totalPermit = 1555;
            totalPermit += (totalYear - 10) * 24;
            if (totalPermit < dao.calculateTotalPermit(person, type)) {
                createRuntimeException("ANP.0010", "İzin hakkınız bulunmamaktadır!", null);
            }
        }
    }

    @Override
    public AnnualPermit actNew() throws Exception {
        AnnualPermit annualPermit = super.actNew();
        annualPermit.setPerson(session.getPerson());
        return annualPermit;
    }

    public JsonArray checkOfficialHolidays(LocalDate localDate) throws Exception {
        String sURL = "https://www.googleapis.com/calendar/v3/calendars/tr.turkish%23holiday%40group.v.calendar.google.com/events?key=AIzaSyAA7SjdUVkPjToQ4Z6BYGn5DrdQ7NxIeL4"; //just a string
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
        return jsonElement.getAsJsonObject().get("items").getAsJsonArray();
    }
}

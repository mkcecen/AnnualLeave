package com.annualLeave.service;


import com.annualLeave.dao.LoginDao;
import com.annualLeave.entity.Login;
import com.annualLeave.entity.Person;
import com.annualLeave.enums.Language;
import com.annualLeave.framework.abstracts.AbstractService;
import com.annualLeave.framework.exceptions.AuthenticateException;
import com.annualLeave.framework.security.JwtUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LoginService extends AbstractService<Login> {

    private PersonService personService;
    private JwtUtil jwtUtil;
    private LoginDao dao;

    public LoginService(LoginDao dao, @Lazy PersonService personService, JwtUtil jwtUtil) {
        this.dao = dao;
        this.personService = personService;
        this.jwtUtil = jwtUtil;
    }


    public Login login(String email, String password, String language) throws Exception {
        Person person = personService.findFirstByEmailAndPassword(email, password, language);
        if (person == null) {
            if(Language.TR.getValue().equals(language)){
                throw new AuthenticateException("Kullanıcı bulunamadı!");
            }else{
                throw new AuthenticateException("User not found");
            }
        } else {
            if(!person.getLanguage().equals(Language.valueOf(language))){
                person.setLanguage(Language.valueOf(language));
                person = personService.save(person);
            }
            String token = jwtUtil.encrypt(person);
            session.setPerson(person);
            session.setToken(token);
            session.setLanguage(language);

            Login login = actNew();
            login.setPerson(person);
            login.setToken(token);
            login = save(login);
            return login;
        }
    }
}

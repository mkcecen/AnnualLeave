package com.annualLeave.framework.aop;

import javax.naming.NoPermissionException;
import javax.persistence.PersistenceException;

import com.annualLeave.framework.exceptions.CustomException;
import com.annualLeave.framework.security.SessionContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.envers.exception.NotAuditedException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import springfox.documentation.spring.web.json.Json;


import java.io.File;
import java.nio.file.Files;
import java.sql.SQLTransientConnectionException;

@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    private SessionContext sesion;
    private String DEFAULT_LANGUAGE = "TR";


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleGenericException(Exception e, WebRequest request) {
        e.printStackTrace();
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) customExceptionControl(e);
        if (responseEntity != null) {
            return responseEntity;
        }
        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private HttpEntity<? extends Object> customExceptionControl(Exception e) {
        if (e instanceof NoPermissionException) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.FORBIDDEN);
        }else if (e instanceof DataIntegrityViolationException) {
            return getRootCause(e);
        } else if (e instanceof PSQLException) {
            return getRootCause(e);
        } else if (e instanceof InvalidDataAccessResourceUsageException) {
            return getRootCause(e);
        }else if (e instanceof SQLGrammarException) {
            return getRootCause(e);
        } else if (e instanceof PersistenceException) {
            return getRootCause(e);
        } else if (e instanceof NullPointerException) {
            return new ResponseEntity<Object>(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }else if (e instanceof ConstraintViolationException) {
            return getRootCause(e);
        }else if (e instanceof CustomException) {
            CustomException customException = (CustomException) e;
            if(sesion!=null && sesion.getLanguage() != null) {
                DEFAULT_LANGUAGE = sesion.getLanguage();
            }
            String error = findByTranslate(DEFAULT_LANGUAGE, customException.getErrorCode());
            if (error.equals(customException.getErrorCode())) {
                error = customException.getErrorText();

            } else if (error == null || error.trim().isEmpty() || "null".equals(error)) {
                error = customException.getErrorText();
                if (customException.getCustomErrorList() != null && !customException.getCustomErrorList().isEmpty()) {
                    for (int i = 1; i <= customException.getCustomErrorList().size(); i++) {
                        error = error.replaceAll("\\?" + String.valueOf(i), customException.getCustomErrorList().get(i - 1));
                    }
                }
            }
            return new ResponseEntity<String>(error, HttpStatus.BAD_REQUEST);


        }
        return null;
    }

    private ResponseEntity<Object> getRootCause(Throwable throwable) {
        String message = ExceptionUtils.getRootCause(throwable).getMessage();
        return new ResponseEntity<Object>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public String findByTranslate(String language, String erroCode) {
        try {
            File file = ResourceUtils.getFile("classpath:translation/" + language + ".json");

            String content = new String(Files.readAllBytes(file.toPath()));
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(content);
            return  json.get(erroCode).getAsString();
        } catch (Exception e) {
            return erroCode;
        }
    }
}

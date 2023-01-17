package com.annualLeave.framework.exceptions;

import com.annualLeave.framework.generic.GenericEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomException extends Exception {

    private static final long serialVersionUID = 1L;
    String errorCode;
    String errorText;
    List<String> customErrorList;


    public CustomException(String errorCode, String errorText, List<String> customErrorList) {
        this.errorCode = errorCode;
        this.errorText = errorText;
        this.customErrorList = customErrorList;
    }

    public CustomException() {
    }

    public CustomException(String msg) {
        super(msg);
    }

}

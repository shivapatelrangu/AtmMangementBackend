package com.atm.management.api.domain.exceptions;

import com.atm.management.api.domain.enums.CustomHttpStatus;

public class ValidationException extends RuntimeException{

    private CustomHttpStatus customHttpStatus;
    public ValidationException(String message,CustomHttpStatus customHttpStatus){
        super(message);
        this.customHttpStatus=customHttpStatus;
    }
}

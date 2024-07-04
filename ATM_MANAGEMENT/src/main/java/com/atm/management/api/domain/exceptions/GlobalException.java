package com.atm.management.api.domain.exceptions;

import com.atm.management.api.domain.dtos.ExceptionResDto;
import com.atm.management.api.domain.enums.CustomHttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException extends RuntimeException{

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResDto> handleValidationException(ValidationException ex){
        String message = ex.getMessage();
        ExceptionResDto response = new ExceptionResDto(message,false);
        return new ResponseEntity<>(response, HttpStatus.valueOf(CustomHttpStatus.BAD_REQUEST.getCode()));
    }

    @ExceptionHandler(TransactionException.class)
    public  ResponseEntity<String> handleTransactionException(TransactionException e){
        String message = e.getMessage();
        ExceptionResDto response = new ExceptionResDto(message,false);
        return  ResponseEntity.status(CustomHttpStatus.BAD_REQUEST.getCode()).body(message);
//        return new ResponseEntity<>(response, HttpStatus.valueOf(CustomHttpStatus.BAD_REQUEST.getCode()));

    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingParams(MissingServletRequestParameterException ex) {
        String parameterName = ex.getParameterName();
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(parameterName, parameterName + " is required but not provided");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }
}

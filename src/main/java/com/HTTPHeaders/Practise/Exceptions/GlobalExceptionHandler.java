package com.HTTPHeaders.Practise.Exceptions;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFound(AccountNotFoundException exception){
        Map<String, Object> body=new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message",exception.getMessage());
        body.put("status",HttpStatus.NOT_FOUND.value());
        body.put("error",HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(BusinessBalanceException.class)
    public ResponseEntity<Object> handleBusinessBalanceException(BusinessBalanceException exception){
        Map<String, Object> body= new HashMap<>();
        body.put("Timestamp",LocalDateTime.now());
        body.put("status",HttpStatus.UNPROCESSABLE_ENTITY.value());
        body.put("error",HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
        body.put("message",exception.getMessage());
        return new ResponseEntity<>(body,HttpStatus.UNPROCESSABLE_ENTITY );
    }

    @ExceptionHandler(ZeroBalanceException.class)
    public ResponseEntity<Object> handleZeroBalanceException(ZeroBalanceException exception){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status",HttpStatus.UNPROCESSABLE_ENTITY.value());
        body.put("error",HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
        body.put("message", exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }


    @ExceptionHandler(DuplicateIdException.class)
    public ResponseEntity<Object> handleDuplicateidFoundException(DuplicateIdException exception){
        Map<String, Object> body= new HashMap<>();
        body.put("timestamp",LocalDateTime.now());
        body.put("status",HttpStatus.CONFLICT);
        val conflict = HttpStatus.CONFLICT;
        body.put("value", conflict);
        body.put("message",exception.getMessage());

        return new ResponseEntity<>(body,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(inSufficentFundsException.class)
    public ResponseEntity<Object> handleInsufficentFundsException(inSufficentFundsException exception){
        Map<String, Object> body=new HashMap<>();
        body.put("timestamp",LocalDateTime.now());
        body.put("status",HttpStatus.UNPROCESSABLE_ENTITY);

        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}

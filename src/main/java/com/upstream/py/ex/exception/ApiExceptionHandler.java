package com.upstream.py.ex.exception;

import com.upstream.py.ex.exception.customExeption.ApiRequestException;
import com.upstream.py.ex.exception.customExeption.TransactionNotFoundException;
import com.upstream.py.ex.exception.customExeption.UnauthorizedException;
import com.upstream.py.ex.exception.exeptionMessages.ApiExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException ex , WebRequest request){

        ApiExceptionMessage errorMessages = new ApiExceptionMessage( ex.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR , new Date());
        return new ResponseEntity<>(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = TransactionNotFoundException.class)
    public ResponseEntity<Object> handleTransactionNotFoundException(TransactionNotFoundException ex , WebRequest request){

        ApiExceptionMessage errorMessages = new ApiExceptionMessage( ex.getMessage() ,HttpStatus.NOT_FOUND , new Date());
        return new ResponseEntity<>(errorMessages, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorisedException(UnauthorizedException ex , WebRequest request){

        ApiExceptionMessage errorMessages = new ApiExceptionMessage( ex.getMessage() ,HttpStatus.FORBIDDEN , new Date());
        return new ResponseEntity<>(errorMessages, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMusingFieldsException(MethodArgumentNotValidException ex , WebRequest request){

        ApiExceptionMessage errorMessages = new ApiExceptionMessage( ex.getMessage() ,HttpStatus.BAD_REQUEST , new Date());
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleOtherException(Exception ex , WebRequest request){

        ApiExceptionMessage errorMessages = new ApiExceptionMessage(ex.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR ,new Date());
        return new ResponseEntity<>(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
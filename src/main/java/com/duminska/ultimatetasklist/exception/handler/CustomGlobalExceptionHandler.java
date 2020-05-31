package com.duminska.ultimatetasklist.exception.handler;

import com.duminska.ultimatetasklist.exception.ApplicationGlobalException;
import com.duminska.ultimatetasklist.exception.ValidationException;
import com.duminska.ultimatetasklist.exception.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<ErrorMessage> handleException(ApplicationGlobalException exception,
                                                        HttpServletRequest request){

        HttpStatus httpStatus = exception.getHttpStatus();

        log.error(String.format("Exception received, path: '%s'\nException: '%s'",
                request.getRequestURI(), exception));
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(exception.getMessage())
                .statusCode(httpStatus.value())
                .timestamp(System.currentTimeMillis())
                .error(exception.getClass().getName())
                .requestPath(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}

package me.m92.tatbook_web.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    static private final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<Object> handleConflict(BadCredentialsException ex, WebRequest request) {
        LOGGER.error("Bad credentials!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad credentials");
    }
}

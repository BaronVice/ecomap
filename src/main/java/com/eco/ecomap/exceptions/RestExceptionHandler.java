package com.eco.ecomap.exceptions;

import com.eco.ecomap.dtos.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = {ApplicationException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(ApplicationException e){
        logger.error(e.getMessage());

        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorDto(e.getLocalizedMessage()));
    }
}

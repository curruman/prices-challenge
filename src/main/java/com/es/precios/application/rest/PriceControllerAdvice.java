package com.es.precios.application.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice(annotations=RestController.class)
public class PriceControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(PriceControllerAdvice.class);

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, HttpServletRequest request) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        logger.info("executing exception handler (REST): ");
        logger.error(sw.toString());
        return "Error in "+ request.getQueryString() +" :"+ ex.getMessage();
    }

}
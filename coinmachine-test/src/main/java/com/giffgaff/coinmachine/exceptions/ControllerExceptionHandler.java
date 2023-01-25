package com.giffgaff.coinmachine.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  // @ResponseStatus(HttpStatus.NOT_FOUND) // 404
  // @ExceptionHandler(BadCurrenyException.class)
  // public void handleNotFound(BadCurrenyException ex) {
  // // log.error("Request not found");
  // }

  @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
  @ExceptionHandler(IllegalArgumentException.class)
  public String handleBadRequest(IllegalArgumentException ex) {
    return "Currency symbol is not valid";
  }

  // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
  // @ExceptionHandler(Exception.class)
  // public void handleGeneralError(Exception ex) {
  // // log.error("An error occurred processing request" + ex);
  // }

}

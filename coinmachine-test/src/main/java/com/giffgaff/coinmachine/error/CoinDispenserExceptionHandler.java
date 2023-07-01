package com.giffgaff.coinmachine.error;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.giffgaff.coinmachine.error.Outcome.OutcomeType;

@ControllerAdvice
@ResponseStatus
public class CoinDispenserExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CurrencyInvalidException.class)
  public ResponseEntity<Outcome>  handleCurrencyNotValidException(CurrencyInvalidException exception , WebRequest request) {

    Outcome outCome =  new Outcome(OutcomeType.FAILURE, "Currency is invalid");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(outCome);
    
  }

  @ExceptionHandler(CoinListNotFoundException.class)
  public ResponseEntity<Outcome>  handleCurrencyNotValidException(CoinListNotFoundException exception , WebRequest request) {

    Outcome outCome =  new Outcome(OutcomeType.FAILURE, "Not enough coins");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(outCome);
    
  }

  @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatusCode status = ex.getStatusCode();
        String message = ex.getMessage();
        return new ResponseEntity<>(message, status);
    }


}

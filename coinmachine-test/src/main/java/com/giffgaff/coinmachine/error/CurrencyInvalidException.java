package com.giffgaff.coinmachine.error;

public class CurrencyInvalidException extends Exception{

    public CurrencyInvalidException() {
        super();
    }

    public CurrencyInvalidException(String message) {
        super(message);
    }

    public CurrencyInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
    
}

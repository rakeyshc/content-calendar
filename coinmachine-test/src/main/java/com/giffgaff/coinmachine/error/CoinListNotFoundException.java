package com.giffgaff.coinmachine.error;

public class CoinListNotFoundException extends Exception{

    public CoinListNotFoundException() {
        super();
    }

    public CoinListNotFoundException(String message) {
        super(message);
    }

    public CoinListNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
package com.giffgaff.coinmachine.model;

public class CoinInfo {

    private final String denomination;
    private final int value;

    public CoinInfo(String demomiantion, int value){
        this.denomination = demomiantion;
        this.value = value;
    }
    
    public String getDenomination(){
        return denomination;
    }

    public int getValue(){
        return value;
    }
    
}

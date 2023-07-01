package com.giffgaff.coinmachine.coin;

public enum Coins {
    POUND_2("£2", 200),
    POUND_1("£1", 100),
    PENCE_50("50p", 50),
    PENCE_20("20p", 20),
    PENCE_10("10p", 10),
    PENCE_5("5p", 5),
    PENCE_2("2p", 2),
    PENCE_1("1p", 1);

    private final String denomination;
    private final int value;

    private Coins(String denomination, int value) {
        this.denomination = denomination;
        this.value = value;
    }

    public String getDenomination() {
        return denomination;
    }

    public int getValue() {
        return value;
    }
}


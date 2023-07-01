package com.giffgaff.coinmachine.error;

public class Outcome {

    private final OutcomeType outcomeType;
    private final String outcomeMessage;

    public Outcome(OutcomeType outcomeType, String outcomeMessage) {
        this.outcomeType = outcomeType;
        this.outcomeMessage = outcomeMessage;
    }

    public OutcomeType getOutcomeType() {
        return outcomeType;
    }

    public String getOutcomeMessage() {
        return outcomeMessage;
    }

    public enum OutcomeType {
        SUCCESS, FAILURE
    }
}

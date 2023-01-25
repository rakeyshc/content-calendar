package com.giffgaff.coinmachine.controllers;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = "/getChange")
public class CoinChangeController {

    // Assuming a finite set of pence denominations
    private final int[] COIN_TYPES = { 200, 100, 50, 20, 10, 5, 2, 1 };
    private final String[] COIN_DENOMINATIONS = { "£2", "£1", "50p", "20p", "10p", "5p", "2p", "1p" };

    @GetMapping("/dispenseChange")
    public HashMap<String, Integer> fetchChange(@RequestParam String amount) {

        HashMap<String, Integer> coinCount = new HashMap<String, Integer>();

        // Convert the change to be dispensed in pence
        int remainingAmount = (int) (getAmount(amount) * 100);

        for (int i = 0; i < COIN_TYPES.length; i++) {
            int coinNum = remainingAmount / COIN_TYPES[i];
            if (coinNum != 0)
                coinCount.put(COIN_DENOMINATIONS[i], coinNum);
            remainingAmount = remainingAmount % COIN_TYPES[i];
        }
        return coinCount;
    }

    private double getAmount(String amount) {
        double strippedAmount = 0.0;
        try {
            Pattern pattern = Pattern.compile("^£\\d+(\\.\\d{2})?$");
            Matcher matcher = pattern.matcher(amount);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid currency symbol. Only £ is allowed.");
            } else {
                strippedAmount = Double.parseDouble(amount.replaceAll("£", ""));
            }
        } catch (IllegalArgumentException e) {
            // throw IllegalArgumentException;
        }
        return strippedAmount;

    }
}

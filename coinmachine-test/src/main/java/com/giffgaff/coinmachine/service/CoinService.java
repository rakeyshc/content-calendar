package com.giffgaff.coinmachine.service;

import java.util.Map;
import java.util.Optional;

import com.giffgaff.coinmachine.error.CoinListNotFoundException;
import com.giffgaff.coinmachine.error.CurrencyInvalidException;

public interface CoinService {

    public Map<String, Integer> tenderChange(String amount) throws CurrencyInvalidException;

    public Map<String, Integer> getAvailableCoins() throws CoinListNotFoundException;

    //public Map<String, Integer> calculateChange(String amount);

    public Map<String, Integer> saveCoins(Map<String,Integer> coinsMap);

    public Optional<Map<String, Integer>> method2_calculate_change(String amount);
    
}

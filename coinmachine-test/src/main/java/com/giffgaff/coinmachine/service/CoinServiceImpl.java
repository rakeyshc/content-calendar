package com.giffgaff.coinmachine.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.giffgaff.coinmachine.config.CoinInventory;
import com.giffgaff.coinmachine.error.CoinCurencyInvalidException;
import com.giffgaff.coinmachine.error.CoinListNotFoundException;
import com.giffgaff.coinmachine.error.CurrencyInvalidException;
import com.giffgaff.coinmachine.model.CoinInfo;

@Service
public class CoinServiceImpl implements CoinService {

    private CoinInventory coinInventory;

    private final Logger LOGGER = LoggerFactory.getLogger(CoinService.class);

    public CoinServiceImpl(CoinInventory coinInventory) {
        this.coinInventory = coinInventory;
    }

    /**
     * 
     * 
     */
    @Override
    public Map<String, Integer> tenderChange(String amount) throws CurrencyInvalidException {
        final Optional<Double> valueWithOutCurrencySymbol = validateAmount(amount);

        // Time take to Execute the Algorithm for performance...
        long startTime = System.nanoTime();
        Optional<Map<String, Integer>> coins = calculateChange(valueWithOutCurrencySymbol.get().doubleValue());
        long endTime = System.nanoTime();
        
        // Calculate and display execution time...
        long elapsed = (endTime - startTime) / 1_000_000;
        LOGGER.info("It took -->" + elapsed + "<-- to execute the Algorithm");
        return coins.get();
    }

    /**
     * 
     * @param amount
     * @return
     */
    private Optional<Map<String, Integer>> calculateChange(double amount) {
        return coinInventory.calculateChange(amount);
    }

    /**
     * 
     * @param amount
     * @return
     * @throws CurrencyInvalidException
     */
    private Optional<Double> validateAmount(String amount) throws CurrencyInvalidException {
        Pattern pattern = Pattern.compile("^£\\d+(\\.\\d{2})?$");
        Matcher matcher = pattern.matcher(amount);
        if (!matcher.find()) {
            throw new CurrencyInvalidException("Currency is not valid");
        }

        return Optional.of(Double.parseDouble(amount.replaceAll("£", "")));

    }

    @Override
    public Map<String, Integer> getAvailableCoins() throws CoinListNotFoundException {
        Optional<Map<String, Integer>> availableCoins = coinInventory.availableCoins();
        if (availableCoins.isPresent()) {
            return availableCoins.get();
        } else {
            throw new CoinListNotFoundException();
        }
    }

    @Override
    public Map<String, Integer> saveCoins(Map<String, Integer> coinsMap) {

        LOGGER.debug("Admin adds coins " + coinsMap.toString());

        Optional<Map<String, Integer>> availableCoins = coinInventory.availableCoins();
        Map<String, Integer> accumulator = availableCoins.get();
        BiFunction<Integer, Integer, Integer> mapper = (v1, v2) -> v1 + v2;
        coinsMap.forEach((key, value) -> accumulator.merge(key, value, mapper));

        LOGGER.debug("After coins are added " + accumulator.toString());
        return accumulator;
    }

    public Optional<Map<String, Integer>> method2_calculate_change(String amount) {

        BigDecimal decimalAmount = BigDecimal.valueOf(isValidCurrency(amount));
        BigDecimal multipliedAmount = decimalAmount.multiply(BigDecimal.valueOf(100));
        int pence = multipliedAmount.setScale(0, RoundingMode.HALF_UP).intValue();
        
        AtomicInteger penceToDispense = new AtomicInteger(pence);

        BiConsumer<Map<String, Integer>, CoinInfo> consumer = (map, coin) -> {
            if (penceToDispense.intValue() != 0) {
                int number_of_coins = penceToDispense.get() / coin.getValue();
                if (number_of_coins != 0) {
                    penceToDispense.set(penceToDispense.get() % coin.getValue());
                    map.put(coin.getDenomination(), number_of_coins);
                }

            }

        };

        Map<String, Integer> result =  initializeCoins().stream().collect(HashMap<String,Integer>::new,(map,coin) -> consumer.accept(map, coin), Map::putAll);
        return  result.isEmpty() ? Optional.empty() : Optional.of(result);
    }


    private Double isValidCurrency(String amount) {
        Pattern pattern = Pattern.compile("^£\\d+(\\.\\d{2})?$");
        Matcher matcher = pattern.matcher(String.valueOf(amount));
        if(!matcher.find()){
            throw new CoinCurencyInvalidException("Invalid Currency");
        }
        return Double.valueOf(amount.replaceAll("£", ""));
    }

    private List<CoinInfo> initializeCoins() {
        List<CoinInfo> coins = new ArrayList<>();
        coins.add(new CoinInfo("£2", 200));
        coins.add(new CoinInfo("£1", 100));
        coins.add(new CoinInfo("50p", 50));
        coins.add(new CoinInfo("20p", 20));
        coins.add(new CoinInfo("10p", 10));
        coins.add(new CoinInfo("5p", 5));
        coins.add(new CoinInfo("2p", 2));
        coins.add(new CoinInfo("1p", 1));
        return coins;
    }

}

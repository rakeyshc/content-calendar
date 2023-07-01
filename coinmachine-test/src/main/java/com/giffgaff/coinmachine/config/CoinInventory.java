package com.giffgaff.coinmachine.config;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

import com.giffgaff.coinmachine.coin.Coins;


public class CoinInventory {

    private final Map<String,Integer> coins;

    public CoinInventory(){
        coins = new LinkedHashMap<>();
    }


    public void addCoin(String denomination, int count) {
        coins.put(denomination, count);
    }

    public void removeCoin(String denomination, int amount) {
        int count = coins.get(denomination);
        coins.put(denomination, count - amount);
    }

    public int hasCoins(String denomination, int count) {
        int availableCount = coins.getOrDefault(denomination, count);
        if(availableCount - count >=0){
            return count;
        }else if(availableCount - count < 0 && count - availableCount >= 0){
            return availableCount;
        }
        return availableCount;
    }

    public Optional<Map<String,Integer>> availableCoins(){
        return Optional.of(coins);
    }

    public Optional<Map<String, Integer>> calculateChange(double amount) {

        // //Convert the change to be tendered to pence
        //  int pence = (int) (Double.valueOf(amount) * 100);

        // // //Max range is 8 different coins
        // // Map<String, Integer> coins = new LinkedHashMap<>(8);
        //  BiConsumer<String,Integer> consumer = (k,v) -> coins.put(k, v);

        // // //Coins loop map... function {populate coins map based on the conditio
        

        // for (Coins coin : Coins.values()) {
        //     int coinNum = pence / coin.getValue();
        //     int availableCoins = coinInventory.hasCoins(coin.getDenomination(), coinNum);
        //     if (coinNum != 0) {
        //         pence = pence % coin.getValue();
        //         consumer.accept(coin.getDenomination(), availableCoins);
        //         coinInventory.removeCoin(coin.getDenomination(), availableCoins);
        //     }
        // }

        // return coins.isEmpty() ? Optional.empty() : Optional.of(coins);

        AtomicInteger pence = new AtomicInteger((int) (Double.valueOf(amount) * 100));

        BiConsumer<Map<String, Integer>, Coins> coinConsumer = (map, coin) -> {
            int coinNum = pence.get() / coin.getValue();
            int availableCoins = hasCoins(coin.getDenomination(), coinNum);
            if (coinNum != 0) {
                pence.set(pence.get() % coin.getValue());
                map.put(coin.getDenomination(), availableCoins);
                removeCoin(coin.getDenomination(), availableCoins);
            }
        };

        Map<String, Integer> coins = Arrays.stream(Coins.values())
                .collect(LinkedHashMap::new, (map, Coin) -> coinConsumer.accept(map, Coin), Map::putAll);

        return coins.isEmpty() ? Optional.empty() : Optional.of(coins);

        


    }


    
}

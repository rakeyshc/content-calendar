package com.giffgaff.coinmachine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.giffgaff.coinmachine.CurrencyConstants;

@Configuration
public class CoinInventoryConfig  {

    @Bean 
    public CoinInventory coinInventory(){
        CoinInventory coinInventory = new CoinInventory();
        coinInventory.addCoin(CurrencyConstants.£2, 10);
        coinInventory.addCoin(CurrencyConstants.£1, 20);
        coinInventory.addCoin(CurrencyConstants.P50, 30);
        coinInventory.addCoin(CurrencyConstants.P20, 50);
        coinInventory.addCoin(CurrencyConstants.P10, 100);
        coinInventory.addCoin(CurrencyConstants.P5, 200);
        coinInventory.addCoin(CurrencyConstants.P2, 500);
        coinInventory.addCoin(CurrencyConstants.P1, 1000);
        return coinInventory;
    }
    
}

package com.giffgaff.coinmachine.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.giffgaff.coinmachine.error.CoinCurencyInvalidException;
import com.giffgaff.coinmachine.error.CoinListNotFoundException;
import com.giffgaff.coinmachine.error.CurrencyInvalidException;
import com.giffgaff.coinmachine.service.CoinService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class CoinChangeController {

    @Autowired
    private CoinService service;

    @GetMapping("/coins")
    public ResponseEntity<Map<String, Integer>> getAvailableCoins() throws CoinListNotFoundException {
        Map<String, Integer> coinsAvailable = service.getAvailableCoins();
        return ResponseEntity.ok().body(coinsAvailable);
    }

    @GetMapping("/dispense/{amount}")
    public ResponseEntity<Map<String, Integer>> tenderChange(@PathVariable("amount") String amount)
            throws CurrencyInvalidException {
        Map<String, Integer> result = service.tenderChange(amount);
        System.out.println(result.toString());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping(path = "/coins", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Integer>> addCoins(@RequestBody Map<String, Integer> creditCoins)
            throws CurrencyInvalidException {
        Map<String, Integer> result = service.saveCoins(creditCoins);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value="/dispense/v2/{amount}")
    public @ResponseBody Map<String,Integer> calculateChangeTDD(@PathVariable("amount") String amount){

        Optional<Map<String, Integer>> resultMap;
        try{
            resultMap = service.method2_calculate_change(amount);
        }catch(CoinCurencyInvalidException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
        }
        return resultMap.get();
    }

   
}

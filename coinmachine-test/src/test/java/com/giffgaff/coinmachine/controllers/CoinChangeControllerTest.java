package com.giffgaff.coinmachine.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.giffgaff.coinmachine.CurrencyConstants;
import com.giffgaff.coinmachine.error.CoinCurencyInvalidException;
import com.giffgaff.coinmachine.service.CoinService;

//@WebMvcTest(CoinChangeController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@Enable
public class CoinChangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoinService service;

    private Optional<Map<String, Integer>> availableCoins;

    @BeforeEach
    void setUp() {
        // Create a mock object for the Optional<Map<String, Integer>> return type
        availableCoins = Optional.of(new HashMap<>());
        availableCoins.get().put(CurrencyConstants.£2, 10);
        availableCoins.get().put(CurrencyConstants.£1, 20);
        availableCoins.get().put(CurrencyConstants.P50, 30);
        availableCoins.get().put(CurrencyConstants.P20, 50);
        availableCoins.get().put(CurrencyConstants.P10, 100);
        availableCoins.get().put(CurrencyConstants.P5, 200);
        availableCoins.get().put(CurrencyConstants.P2, 500);
        availableCoins.get().put(CurrencyConstants.P1, 1000);
    }

    @Test
    @DisplayName(value="Available coins")
    public void fetchAvailableCoins_inInventory() throws Exception {
        Mockito.when(service.getAvailableCoins()).thenReturn(availableCoins.get());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/coins"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.£2").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.£1").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.50p").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.20p").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.10p").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.5p").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.2p").value(500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.1p").value(1000));

    }

    @Test
    @DisplayName(value="Coins dispensed")
    public void testFetchChange() throws Exception {
        String amount = "£6.46";
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("£2", 3);
        expectedMap.put("20p", 2);
        expectedMap.put("5p", 1);
        expectedMap.put("1p", 1);

        Mockito.when(service.tenderChange(amount)).thenReturn(expectedMap);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/dispense/{amount}", amount))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.£2").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.20p").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.5p").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.1p").value(1));
    }


    @Test
    @Disabled
    @DisplayName(value="Load Coins")
    public void addCoinsInInventory() throws Exception{
        Map<String, Integer> inputMap = new HashMap<>();
        inputMap.put("£2", 5000);
        inputMap.put("£1", 10000);
        inputMap.put("50p", 30000);
        inputMap.put("20p", 2000);
        inputMap.put("5p", 1);
        inputMap.put("1p", 1);

        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("£2", 5000);
        expectedMap.put("£1", 10000);
        expectedMap.put("50p", 30000);
        expectedMap.put("20p", 2000);
        expectedMap.put("5p", 1);
        expectedMap.put("1p", 1);

        Mockito.when(service.saveCoins(inputMap)).thenReturn(expectedMap);

         String contents = "";
        //         {
        //             "£2" : 50000,
        //             "£1" : 100000,
        //             "50p" : 250000,
        //             "20p" : 500000,
        //             "10p" : 30000,
        //             "5p" : 25000,
        //             "2p" : 10000,
        //             "1p" : 25000

        //         }
        //             """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/coins").contentType(MediaType.APPLICATION_JSON).content(
            contents)).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void tenderChange() throws Exception{

        String amount = "£6.46";
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("£2", 3);
        expectedMap.put("20p", 2);
        expectedMap.put("5p", 1);
        expectedMap.put("1p", 1);

         Mockito.when(service.method2_calculate_change(amount)).thenReturn(Optional.of(expectedMap));

         mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/dispense/{amount}", amount)
                .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void tenderChangeFail() throws Exception{

        String amount = "$6.46";
        Mockito.when(service.method2_calculate_change(amount)).thenThrow(CoinCurencyInvalidException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v2/dispense/{amount}", amount).accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
        
    }


}

package com.giffgaff.coinmachine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.giffgaff.coinmachine.config.CoinInventory;
import com.giffgaff.coinmachine.error.CoinCurencyInvalidException;
import com.giffgaff.coinmachine.error.CurrencyInvalidException;

@DisplayName(value="A Coin Machine")
public class CoinServiceTest {

    @Autowired
    private CoinService service;

    private Map<String, Integer> resultMap = new HashMap<>();

    @BeforeEach
    void setUp() {
        service = Mockito.mock(CoinService.class);

    }

    @Test
    @DisplayName(value = "tenders change with best combination")
    public void tender_change_for_given_amount() throws CurrencyInvalidException {
        // Populate Map
        resultMap.put("£2", 2);
        resultMap.put("20p", 2);
        resultMap.put("5p", 1);
        resultMap.put("1p", 1);

        Mockito.when(service.tenderChange("4.46")).thenReturn(resultMap);
        Map<String, Integer> actualResult = service.tenderChange("4.46");
        assertEquals(resultMap, actualResult);
    }

    @Test
    @DisplayName(value = "Valid Currency symbol")
    public void tenderChangeWithCurrencySymbol£() throws CurrencyInvalidException {
        // Populate Map
        resultMap.put("£2", 2);
        resultMap.put("20p", 2);
        resultMap.put("5p", 1);
        resultMap.put("1p", 1);

        Mockito.when(service.tenderChange("£4.46")).thenReturn(resultMap);
        Map<String, Integer> actualResult = service.tenderChange("£4.46");
        assertEquals(resultMap, actualResult);
    }

    @Test
    @DisplayName(value = "Invalid Currency symbol")
    public void tenderChangeWithCurrencySymbol$() throws CurrencyInvalidException {
        // Populate Map
        resultMap.put("£2", 2);
        resultMap.put("20p", 2);
        resultMap.put("5p", 1);
        resultMap.put("1p", 1);

        Mockito.when(service.tenderChange("$4.46")).thenThrow(CurrencyInvalidException.class);
        assertThrows(CurrencyInvalidException.class, () -> service.tenderChange("$4.46"));
        assertThrowsExactly(CurrencyInvalidException.class, () -> service.tenderChange("$4.46"),() -> "");
    }

    @Test
    @DisplayName(value = "Add coins to Inventory")
    public void saveCoinsWithSuppliedDenominationsAndValues(){
        Map<String, Integer> inputMap = new HashMap<>();
        inputMap.put("£2", 5000);
        inputMap.put("£1", 10000);
        inputMap.put("50p", 30000);
        inputMap.put("20p", 2000);
        inputMap.put("5p", 1);
        inputMap.put("1p", 1);

        Map<String, Integer> expected = new HashMap<>();
        inputMap.put("£2", 5000);
        inputMap.put("£1", 10000);
        inputMap.put("50p", 30000);
        inputMap.put("20p", 2000);
        inputMap.put("5p", 1);
        inputMap.put("1p", 1);

        Mockito.when(service.saveCoins(inputMap)).thenReturn(expected);
        Map<String,Integer> actualResult = service.saveCoins(inputMap);
        assertEquals(expected,actualResult);


    }

    /*
     *  |Input| Output                         |
        |£2   | 1 £2                           |
        |253  | 1 £2, 1 50p, 1 2p, 1 1p        |
        |5.23 | 2 £2, 1 £1, 1 20p, 1 2p, 1 1p  |
        |$23  | Throw sensible exception       |
     * 
     * 
     * 
     */

    @Nested
    public class TestExercise{

        @DisplayName(value="calculates £2 ->  1 £2 ")
        @Test
        public void test_calculate_change_on_correct_input(){
            Map<String, Integer> expected = new HashMap<>();
            expected.put("£2", 1);
            // Mockito.when(service.method2_calculate_change("£2.0")).thenReturn(Optional.of(expected));
            // Optional<Map<String, Integer>> actual = service.method2_calculate_change("£2.0"); 
            // assertEquals(expected, actual.get());
            //fail("Not implemented");

            CoinInventory inventory = Mockito.mock(CoinInventory.class);
            CoinService coinService = new CoinServiceImpl(inventory);
            Optional<Map<String, Integer>> coins = coinService.method2_calculate_change("£2");
            assertThat(coins.get()).isEqualTo(expected);

        }

        @DisplayName(value="calculates |253  | 1 £2, 1 50p, 1 2p, 1 1p ")
        @Test
        public void test_calculate_change_on_correct_input_2(){
            Map<String, Integer> expected = new HashMap<>();
            expected.put("£2", 1);
            expected.put("50p", 1);
            expected.put("2p", 1);
            expected.put("1p", 1);
            // Mockito.when(service.method2_calculate_change("£2.0")).thenReturn(Optional.of(expected));
            // Optional<Map<String, Integer>> actual = service.method2_calculate_change("£2.0"); 
            // assertEquals(expected, actual.get());
            //fail("Not implemented");

            CoinInventory inventory = Mockito.mock(CoinInventory.class);
            CoinService coinService = new CoinServiceImpl(inventory);
            Optional<Map<String, Integer>> coins = coinService.method2_calculate_change("£2.53");
            assertThat(coins.get()).isEqualTo(expected);

        }

        @DisplayName(value="calculates |5.23 | 2 £2, 1 £1, 1 20p, 1 2p, 1 1p  | ")
        @Test
        public void test_calculate_change_on_correct_input_3(){
            Map<String, Integer> expected = new HashMap<>();
            expected.put("£2", 2);
            expected.put("£1", 1);
            expected.put("20p", 1);
            expected.put("2p", 1);
            expected.put("1p", 1);
            // Mockito.when(service.method2_calculate_change("£2.0")).thenReturn(Optional.of(expected));
            // Optional<Map<String, Integer>> actual = service.method2_calculate_change("£2.0"); 
            // assertEquals(expected, actual.get());
            //fail("Not implemented");

            CoinInventory inventory = Mockito.mock(CoinInventory.class);
            CoinService coinService = new CoinServiceImpl(inventory);
            Optional<Map<String, Integer>> coins = coinService.method2_calculate_change("£5.23");
            assertThat(coins.get()).isEqualTo(expected);

        }

        @DisplayName(value="calculates |253  | 1 £2, 1 50p, 1 2p, 1 1p ")
        @Test
        public void test_calculate_change_on_correct_input_4(){
            Map<String, Integer> expected = new HashMap<>();
            expected.put("£2", 1);
            expected.put("50p", 1);
            expected.put("2p", 1);
            expected.put("1p", 1);
            // Mockito.when(service.method2_calculate_change("£2.0")).thenReturn(Optional.of(expected));
            // Optional<Map<String, Integer>> actual = service.method2_calculate_change("£2.0"); 
            // assertEquals(expected, actual.get());
            //fail("Not implemented");

            CoinInventory inventory = Mockito.mock(CoinInventory.class);
            CoinService coinService = new CoinServiceImpl(inventory);
            Optional<Map<String, Integer>> coins = coinService.method2_calculate_change("£2.53");
            assertThat(coins.get()).isEqualTo(expected);

        }

        @DisplayName(value = "Coin |$23  | Throw sensible exception ")
        @Test
        public void test_calculate_validate() {
            // Map<String, Integer> expected = new HashMap<>();
            // expected.put("£2", 1);
            // Mockito.when(service.method2_calculate_change("£2.0")).thenReturn(Optional.of(expected));
            // Optional<Map<String, Integer>> actual =
            CoinInventory inventory = Mockito.mock(CoinInventory.class);
            CoinService coinService = new CoinServiceImpl(inventory);
           //  service.method2_calculate_change("&2.0");
            assertThatThrownBy(() -> coinService.method2_calculate_change("$2.0"))
                    .isInstanceOf(CoinCurencyInvalidException.class).hasMessageContaining("Invalid Currency");
            // fail("Not implemented");

        }

    }
}

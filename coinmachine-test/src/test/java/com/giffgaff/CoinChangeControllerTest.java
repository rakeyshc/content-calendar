package com.giffgaff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@RunsWith(SpringRunner.class)
@SpringBootTest
public class CoinChangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFetchChange(String amount) {
        mockMvc.perform(get("", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is("resource1")));
    }

}

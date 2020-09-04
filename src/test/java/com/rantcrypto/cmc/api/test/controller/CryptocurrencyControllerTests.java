package com.rantcrypto.cmc.api.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rantcrypto.cmc.api.ApiExceptionHandler;
import com.rantcrypto.cmc.api.controller.CryptocurrencyController;

/**
 * Credit cost of all tests combined: 7
 * 
 * @author Phillip Groves
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CryptocurrencyControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private CryptocurrencyController cryptocurrencyController;
    
    @Autowired
    private ApiExceptionHandler exceptionHandler;
    
    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
        		.standaloneSetup(this.cryptocurrencyController)
        		.setControllerAdvice(this.exceptionHandler).build();
    }
    
    @Test
    void testMapStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/map?start=1&limit=5"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        
        // covering edge case of start, limit being null
        result = mockMvc
        		.perform(get("/v1/cryptocurrency/map"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testInfoStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/info?id=1"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testListingsLatestStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/listings/latest?start=1&limit=5&price_min=0&price_max=1000&market_cap_min=0&market_cap_max=1000000000000&volume_24h_min=0&volume_24h_max=1000000000000&circulating_supply_min=0&circulating_supply_max=1000000000000&percent_change_24h_min=-100&percent_change_24h_max=100"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        
        // covering edge case of certain fields being null
        result = mockMvc
        		.perform(get("/v1/cryptocurrency/listings/latest"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testQuotesLatestStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/quotes/latest?id=1&skip_invalid=false"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        
        // covering edge case of skip_invalid field being null
        result = mockMvc
        		.perform(get("/v1/cryptocurrency/quotes/latest?id=1"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
}

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
import com.rantcrypto.cmc.api.controller.ExchangeController;

/**
 * Credit cost of all tests combined: 2
 * <p><b>If you are not connecting to the sandbox API, some of these tests 
 * may fail depending on your CoinMarketCap plan.</b></p>
 * 
 * @author Phillip Groves
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExchangeControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private ExchangeController exchangeController;
    
    @Autowired
    private ApiExceptionHandler exceptionHandler;
    
    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
        		.standaloneSetup(this.exchangeController)
        		.setControllerAdvice(this.exceptionHandler).build();
    }
    
    @Test
    void testMapStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/exchange/map?start=1&limit=5"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        
        // covering edge case of start, limit being null
        result = mockMvc
        		.perform(get("/v1/exchange/map"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testInfoStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/exchange/info?id=270"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testListingsLatestStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/exchange/listings/latest?start=1&limit=5"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        
        // covering edge case of start, limit being null
        result = mockMvc
        		.perform(get("/v1/exchange/listings/latest"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testListingsHistoricalStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/exchange/listings/historical"))
        		.andExpect(status().isNotFound())
        		.andReturn().getResponse();
        
        // Not yet implemented by CoinMarketCap, will always return 404
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
    }
    
    @Test
    void testQuotesLatestStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/exchange/quotes/latest?id=270"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testQuotesHistoricalStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/exchange/quotes/historical?id=270"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testMarketPairsLatestStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/exchange/market-pairs/latest?id=270&start=1&limit=5"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        
        // covering edge case of start, limit being null
        result = mockMvc
        		.perform(get("/v1/exchange/market-pairs/latest?id=270"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
}

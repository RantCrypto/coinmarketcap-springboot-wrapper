package com.rantcrypto.cmc.api.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
 * <p>Credit cost of all tests combined: 18</p>
 * <p><b>If you are not connecting to the sandbox API, some of these tests 
 * may fail depending on your CoinMarketCap plan.</b></p>
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
    void testListingsHistoricalStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/listings/historical?date=2020-09-09&start=1&limit=5"))
        		.andReturn().getResponse();
        
    	// CoinMarketCap (sandbox) API seems to be bugged at time of writing, will always return date out of range response
    	String response = result.getContentAsString();
    	assertTrue(response.contains("Search query is out of range. Please select dates between"));
    
        // covering edge case of start, limit fields being null
        result = mockMvc
        		.perform(get("/v1/cryptocurrency/listings/historical?date=2020-09-09"))
        		.andReturn().getResponse();
        
        response = result.getContentAsString();
    	assertTrue(response.contains("Search query is out of range. Please select dates between"));
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
    
    @Test
    void testQuotesHistoricalStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/quotes/historical?id=1&count=10"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        
        // covering edge case of count field being null
        result = mockMvc
        		.perform(get("/v1/cryptocurrency/quotes/historical?id=1"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testMarketPairsLatestStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/market-pairs/latest?id=1&start=1&limit=5"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        
        // covering edge case of start, limit fields being null
        result = mockMvc
        		.perform(get("/v1/cryptocurrency/market-pairs/latest?id=1"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testOhlcvLatestStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/ohlcv/latest?symbol=BTC&skip_invalid=false"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        
        // covering edge case of skip_invalid fields being null
        result = mockMvc
        		.perform(get("/v1/cryptocurrency/ohlcv/latest?symbol=BTC"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testOhlcvHistoricalStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/ohlcv/historical?symbol=BTC&count=10&skip_invalid=true"))
        		.andReturn().getResponse();
        
    	// Cannot find an id or symbol that works, so checking CoinMarketCap API response 
        // for "No results found." We are simply checking connection to the correct endpoint.
    	String response = result.getContentAsString();
    	assertTrue(response.contains("No results found."));
    
        // covering edge case of count, skip_invalid fields being null
        result = mockMvc
        		.perform(get("/v1/cryptocurrency/ohlcv/historical?symbol=BTC"))
        		.andReturn().getResponse();
        
        response = result.getContentAsString();
    	assertTrue(response.contains("No results found."));
    }
    
    @Test
    void testPricePerformanceStatsLatestStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/price-performance-stats/latest?id=1"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
}

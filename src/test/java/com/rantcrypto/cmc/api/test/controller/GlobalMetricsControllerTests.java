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
import com.rantcrypto.cmc.api.controller.GlobalMetricsController;

/**
 * Credit cost of all tests combined: 3
 * 
 * @author Phillip Groves
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GlobalMetricsControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private GlobalMetricsController globalMetricsController;
    
    @Autowired
    private ApiExceptionHandler exceptionHandler;
    
    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
        		.standaloneSetup(this.globalMetricsController)
        		.setControllerAdvice(this.exceptionHandler).build();
    }
    
    @Test
    void testQuotesLatestStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/global-metrics/quotes/latest"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testQuotesHistoricalStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/global-metrics/quotes/historical"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        
        // covering edge case of start, limit being null
        result = mockMvc
        		.perform(get("/v1/global-metrics/quotes/historical?count=10"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
}

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
import com.rantcrypto.cmc.api.controller.PartnersController;

/**
 * Credit cost of all tests combined: 3
 * 
 * @author Phillip Groves
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PartnersControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private PartnersController partnersController;
    
    @Autowired
    private ApiExceptionHandler exceptionHandler;
    
    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
        		.standaloneSetup(this.partnersController)
        		.setControllerAdvice(this.exceptionHandler).build();
    }
    
    @Test
    void testFlipsideCryptoFcasListingsLatestStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/partners/flipside-crypto/fcas/listings/latest?start=1&limit=5"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), result.getStatus());
        
        // covering edge case of start and limit not supplied
        result = mockMvc
        		.perform(get("/v1/partners/flipside-crypto/fcas/listings/latest"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
    
    @Test
    void testFlipsideCryptoFcasQuotesLatestStatusOk() throws Exception {
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/partners/flipside-crypto/fcas/quotes/latest?id=1"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }
}

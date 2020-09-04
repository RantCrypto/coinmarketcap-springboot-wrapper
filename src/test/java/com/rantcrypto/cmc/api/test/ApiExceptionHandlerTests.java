package com.rantcrypto.cmc.api.test;

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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiExceptionHandlerTests {

    private MockMvc mockMvc;

    /* Use the cryptocurrency API because it has endpoints with parameters of every type 
     * and each costs 1 credit */
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
    void shouldBeIntegerNumber() throws Exception {
    	String predictedResult = "\\\"" + "start" + "\\\"" + " must be a " + "number";
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/listings/latest?start=steve&limit=5"))
        		.andExpect(status().isBadRequest())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
        assertTrue(result.getContentAsString().contains(predictedResult));
    }
    
    @Test
    void shouldBeFloatingNumber() throws Exception {
    	String predictedResult = "\\\"" + "price_min" + "\\\"" + " must be a " + "number";
    	
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/listings/latest?start=1&limit=5&price_min=bill"))
        		.andExpect(status().isBadRequest())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
        assertTrue(result.getContentAsString().contains(predictedResult));
    }
    
    @Test
    void shouldBeBoolean() throws Exception {
    	String predictedResult = "\\\"" + "skip_invalid" + "\\\"" + " must be a " + "boolean";
    	
        MockHttpServletResponse result = mockMvc
        		.perform(get("/v1/cryptocurrency/quotes/latest?start=1&limit=5&skip_invalid=brett"))
        		.andExpect(status().isBadRequest())
        		.andReturn().getResponse();
        
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
        assertTrue(result.getContentAsString().contains(predictedResult));
    }
}

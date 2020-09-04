package com.rantcrypto.cmc.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.rantcrypto.cmc.api.ApiException;

@SpringBootTest
public class ApiExceptionTests {
	
	
	@Test
	public void testConstructor() {
		Long errorCode = 500L;
		String errorMessage = "\"" + "start" + "\"" + " must be a " + "number";
		
		// Initialize the object with test data
		ApiException exception = new ApiException(errorCode, errorMessage);
		
		// The exception status object should not be null, and nor should the provided fields
		assertNotNull(exception.getStatus());
		assertNotNull(exception.getStatus().getTimestamp());
		assertNotNull(exception.getStatus().getError_message());
		
		// Assert errorCode and message are set from Constructor
		assertEquals(errorCode, exception.getStatus().getError_code());
		assertEquals(errorMessage, exception.getStatus().getError_message());
	}
	
	@Test
	public void testGetStatus() {
		Long errorCode = 500L;
		String errorMessage = "\"" + "start" + "\"" + " must be a " + "number";
		
		ApiException exception = new ApiException(errorCode, errorMessage);
		
		// The exception status object should not be null, and nor should any of its members
		assertNotNull(exception.getStatus());
		assertNotNull(exception.getStatus().getTimestamp());
		assertNotNull(exception.getStatus().getError_message());
		assertNotNull(exception.getStatus().getElapsed());
		assertNotNull(exception.getStatus().getCredit_count());
		assertNotNull(exception.getStatus().toString());
		
		// Ensure errorCode and message are set from Constructor; check if dynamic fields are instantiated
		assertEquals(errorCode, exception.getStatus().getError_code());
		assertEquals(errorMessage, exception.getStatus().getError_message());
	}
}

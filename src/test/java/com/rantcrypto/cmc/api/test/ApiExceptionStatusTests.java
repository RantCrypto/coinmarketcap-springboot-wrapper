package com.rantcrypto.cmc.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.rantcrypto.cmc.api.model.ApiExceptionStatus;

@SpringBootTest
public class ApiExceptionStatusTests {
	
	
	@Test
	public void testConstructor() {
		String timestamp = "Beer-Thirty";
		Long errorCode = 404L;
		String errorMessage = "Beer not Found";
		Long elapsed = 0L;
		Long creditCount = 0L;
		
		// Initialize the object with test data
		ApiExceptionStatus status = new ApiExceptionStatus(timestamp, errorCode, errorMessage, elapsed, creditCount);

		// The status object should not be null, and nor should the provided fields
		assertNotNull(status);
		assertNotNull(status.getTimestamp());
		assertNotNull(status.getError_code());
		assertNotNull(status.getError_message());
		assertNotNull(status.getElapsed());
		assertNotNull(status.getCredit_count());
		
		// Assert fields are set from Constructor
		assertEquals(timestamp, status.getTimestamp());
		assertEquals(errorCode, status.getError_code());
		assertEquals(errorMessage, status.getError_message());
		assertEquals(elapsed, status.getElapsed());
		assertEquals(creditCount, status.getCredit_count());
	}
	
	@Test
	public void testGettersSetters() {
		String timestamp = "Beer-Thirty";
		Long errorCode = 404L;
		String errorMessage = "Beer not Found";
		Long elapsed = 0L;
		Long creditCount = 0L;
		
		// Initialize the object with test data
		ApiExceptionStatus status = new ApiExceptionStatus();
		status.setTimestamp(timestamp);
		status.setError_code(errorCode);
		status.setError_message(errorMessage);
		status.setElapsed(elapsed);
		status.setCredit_count(creditCount);
		
		// Assert fields are set and can be gotten not null
		assertNotNull(status);
		assertNotNull(status.getTimestamp());
		assertNotNull(status.getError_code());
		assertNotNull(status.getError_message());
		assertNotNull(status.getElapsed());
		assertNotNull(status.getCredit_count());
		
		// Assert fields are set and can be gotten matching the supplied data
		assertEquals(timestamp, status.getTimestamp());
		assertEquals(errorCode, status.getError_code());
		assertEquals(errorMessage, status.getError_message());
		assertEquals(elapsed, status.getElapsed());
		assertEquals(creditCount, status.getCredit_count());
	}
}

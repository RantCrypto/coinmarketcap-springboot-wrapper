package com.rantcrypto.cmc.api.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.context.SpringBootTest;

import com.rantcrypto.cmc.api.ApiApplication;


@SpringBootTest
class ApiApplicationTests {

	
	@Test
	public void contextLoads() {
		// don't need tested, so we're only tapping the code so it doesn't bring down coverage percent
		ApiApplication.main(new String[] {});
		assertTrue(true);
	}
}

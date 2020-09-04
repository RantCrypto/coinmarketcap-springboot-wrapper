/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point to the Spring Boot RESTful API. This application will start then wait for requests 
 * on REST endpoints. Each endpoint correlates to the CoinMarketCap API beginning at version 
 * number (e.g. /v1/).
 * 
 * @author Phillip Groves
 */
@SpringBootApplication
public class ApiApplication {

	/**
	 * Entry point to the Spring Boot RESTful API
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}

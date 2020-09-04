/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rantcrypto.cmc.api.service.KeyService;

/**
 * <p>API endpoints for managing your API key. This category currently includes 1 endpoint:</p>
 * <ul>
 * 	<li><b>/v1/key/info -</b> Key info</li>
 * </ul>
 * 
 * @author Phillip Groves
 */
@RestController
@RequestMapping(path = "v1/key")
public class KeyController {

	@Autowired
	private KeyService keyService;
	
	/**
	 * <p>Returns API key details and usage stats. This endpoint can be used to programmatically monitor your key usage compared to the rate limit and daily/monthly credit limits available to your API plan. You may use the Developer Portal's account dashboard as an alternative to this endpoint.</p>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Basic</li>
	 * 	<li>Hobbyist</li>
	 * 	<li>Startup</li>
	 * 	<li>Standard</li>
	 * 	<li>Professional</li>
	 * 	<li>Enterprise</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> No cache, this endpoint updates as requests are made with your key.</p>
	 * <p><b>Plan credit use:</b> No API credit cost. Requests to this endpoint do contribute to your minute based rate limit however.</p>
	 * 
	 * @return API key details and usage stats.
	 */
	@GetMapping(path = "info", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getInfo() {
		return this.keyService.getInfo();
	}
}

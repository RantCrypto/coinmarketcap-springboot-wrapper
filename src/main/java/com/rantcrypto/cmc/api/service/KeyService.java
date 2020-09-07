/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * <p>Service worker layer for {@link com.rantcrypto.cmc.api.controller.KeyController }</p>
 * 
 * @author Phillip Groves
 * @author CoinMarketCap (documentation / data)
 */
@Service
public class KeyService extends CoinMarketCapService {

	@Value("${com.rantcrypto.cmc.api.v1.key.info-endpoint:/v1/key/info}") 
	private String KEY_INFO_ENDPOINT;
	
	
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
	public ResponseEntity<Object> getInfo() {	
		return super.getResponseFromEndpoint(KEY_INFO_ENDPOINT);
	}
}

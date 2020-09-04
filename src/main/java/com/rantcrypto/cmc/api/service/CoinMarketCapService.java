/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * Parent to all services which act on data from the CoinMarketCap API. Holds convenience fields 
 * and methods that are used for different API endpoints.
 * 
 * @author Phillip Groves
 */
@Service
public abstract class CoinMarketCapService {
	
	@Value("${com.rantcrypto.cmc.api.v1.base-url:https://sandbox-api.coinmarketcap.com}")
	private String CMC_BASE_URL;
	
	@Value("${com.rantcrypto.cmc.api.v1.header:X-CMC_PRO_API_KEY}")
	private String CMC_HEADER;
	
	@Value("${com.rantcrypto.cmc.api.v1.api-key}")
	private String CMC_API_KEY;
	
	private HttpEntity<String> httpEntity;
	
	
	/**
	 * Sends a request to the CoinMarketCap REST API at the given endpoint. Endpoint string should 
	 * begin with /v[version]/ (e.g. /v1/) and should not include query parameters. To include query 
	 * parameters, supply a HashMap<String, String> HashMap of key-value pairs.
	 * 
	 * @param endpoint Endpoint URI beginning with /v[version]/
	 * @param paramMap Request query parameters
	 * 
	 * @return JSON response
	 */
	public ResponseEntity<String> getResponseFromEndpoint(String endpoint, HashMap<String, String> paramMap) {
		
		
		if (this.httpEntity == null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set(CMC_HEADER, CMC_API_KEY);
			
			this.httpEntity = new HttpEntity<String>("parameters", headers);
		}
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		String formattedUrl = CMC_BASE_URL + endpoint;
		
		if (paramMap != null && paramMap.entrySet().size() > 0) 
			formattedUrl += this.getFormattedParams(paramMap);
		
		try {
			response = restTemplate.exchange(
					formattedUrl, HttpMethod.GET, httpEntity, String.class);
			
		} catch (HttpStatusCodeException exception) {
		    return new ResponseEntity<String>(exception.getResponseBodyAsString(), exception.getStatusCode());
		}
		
		return new ResponseEntity<String>((response != null ? response.getBody() : null), HttpStatus.OK);
	}
	
	/**
	 * Sends a request to the CoinMarketCap REST API at the given endpoint. Endpoint string should 
	 * begin with /v[version]/ (e.g. /v1/) and should not include query parameters. To include query 
	 * parameters, supply a HashMap<String, String> HashMap of key-value pairs.
	 * 
	 * @param endpoint Endpoint URI beginning with /v[version]/
	 * 
	 * @return JSON response
	 */
	public ResponseEntity<String> getResponseFromEndpoint(String endpoint) {
		return getResponseFromEndpoint(endpoint, null);
	}
	
	/**
	 * Transforms a HashMap of REST endpoint query parameters into a properly formatted 
	 * string of query parameters. The key of the HashMap is the key of the query parameter. 
	 * The value of the HashMap is the value of the query parameter.
	 * 
	 * @param paramMap HashMap of key-value pairs
	 * @return Formatted query parameters that can be appended to an endpoint
	 */
	public String getFormattedParams(HashMap<String, String> paramMap) {
		StringBuilder paramStringBuilder = new StringBuilder("");
		if (paramMap != null) {
			boolean isFirstParam = true;
			
			for (Map.Entry<String,String> entry : paramMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				
				if (key != null && !key.isEmpty() && value != null && !value.isEmpty()) {
					paramStringBuilder.append((isFirstParam ? "?" : "&") + key + "=" + value);
					isFirstParam = false;
				}
			}
		}
		return paramStringBuilder.toString();
	}
}

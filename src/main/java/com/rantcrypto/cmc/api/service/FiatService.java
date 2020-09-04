/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * <p>Service worker layer for {@link com.rantcrypto.cmc.api.controller.FiatController }</p>
 * 
 * @author Phillip Groves
 * @author CoinMarketCap (documentation / data)
 */
@Service
public class FiatService extends CoinMarketCapService {

	@Value("${com.rantcrypto.cmc.api.v1.fiat.map-endpoint:/v1/fiat/map}") 
	private String FIAT_MAP_ENDPOINT;
	

	/**
	 * <p>Returns a mapping of all supported fiat currencies to unique CoinMarketCap ids. Per CoinMarketCap Best Practices we recommend utilizing CMC ID instead of currency symbols to securely identify assets with our other endpoints and in your own application logic.</p>
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
	 * <p><b>Cache / Update frequency:</b> Mapping data is updated only as needed, every 30 seconds.</p>
	 * <p><b>Plan credit use:</b> 1 API call credit per request no matter query size.</p>
	 * 
	 * @param start Optionally offset the start (1-based index) of the paginated list of items to return. Default: 1
	 * @param limit Optionally specify the number of results to return. Use this parameter and the "start" parameter to determine your own pagination size.
	 * @param sort What field to sort the list by. Default: "id". Valid values: "name", "id"
	 * @param includeMetals Pass true to include precious metals. Default: false
	 * 
	 * @return Mapping of all supported fiat currencies to unique CoinMarketCap ids.
	 */
	public ResponseEntity<String> getMap(Long start, Long limit, String sort, Boolean includeMetals) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("start", (start != null ? Long.toString(start) : null));
		paramMap.put("limit", (limit != null ? Long.toString(limit) : null));
		paramMap.put("sort", sort);
		paramMap.put("include_metals", (includeMetals != null ? Boolean.toString(includeMetals) : null));
		
		return super.getResponseFromEndpoint(FIAT_MAP_ENDPOINT, paramMap);
	}
}

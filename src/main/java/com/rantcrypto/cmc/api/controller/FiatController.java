/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rantcrypto.cmc.api.service.FiatService;

/**
 * <p>API endpoints for fiat currencies. This category currently includes 1 endpoint:</p>
 * <ul>
 * 	<li><b>/v1/fiat/map -</b> CoinMarketCap ID map</li>
 * </ul>
 * 
 * @author Phillip Groves
 */
@RestController
@RequestMapping(path = "v1/fiat")
public class FiatController {

	@Autowired
	private FiatService fiatService;
	
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
	@GetMapping(path = "map", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMap(
			@RequestParam(required = false) Long start, 
			@RequestParam(required = false) Long limit, 
			@RequestParam(required = false) String sort, 
			@RequestParam(required = false, name = "include_metals") Boolean includeMetals) {
		return this.fiatService.getMap(start, limit, sort, includeMetals);
	}
}

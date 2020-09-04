/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * <p>Service worker layer for {@link com.rantcrypto.cmc.api.controller.PartnersController }</p>
 * 
 * @author Phillip Groves
 * @author CoinMarketCap (documentation / data)
 */
@Service
public class PartnersService extends CoinMarketCapService {

	@Value("${com.rantcrypto.cmc.api.v1.partners.flipside-crypto-fcas-listings-latest-endpoint:/v1/partners/flipside-crypto/fcas/listings/latest}") 
	private String PARTNERS_FLIPSIDE_CRYPTO_FCAS_LISTINGS_LATEST_ENDPOINT;
	
	@Value("${com.rantcrypto.cmc.api.v1.partners.flipside-crypto-fcas-quotes-latest-endpoint:/v1/partners/flipside-crypto/fcas/quotes/latest}") 
	private String PARTNERS_FLIPSIDE_CRYPTO_FCAS_QUOTES_LATEST_ENDPOINT;
	
	
	/**
	 * <p>Returns a paginated list of FCAS scores for all cryptocurrencies currently supported by FCAS. FCAS ratings are on a 0-1000 point scale with a corresponding letter grade and is updated once a day at UTC midnight.</p>
	 * <p>FCAS stands for Fundamental Crypto Asset Score, a single, consistently comparable value for measuring cryptocurrency project health. FCAS measures User Activity, Developer Behavior and Market Maturity and is provided by FlipSide Crypto. Users interested in FCAS historical data including sub-component scoring may inquire through our CSV Data Delivery request form.</p>
	 * <p><b>Disclaimer:</b> Ratings that are calculated by third party organizations and are not influenced or endorsed by CoinMarketCap in any way.</p>
	 * 
	 * <p><b>NOTE: Use this endpoint to request the latest FCAS score for all supported cryptocurrencies at the same time. If you require FCAS for only specific cryptocurrencies use /v1/partners/flipside-crypto/fcas/quotes/latest which is optimized for that purpose. The response data between these endpoints is otherwise the same.</b></p>
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
	 * <p><b>Cache / Update frequency:</b> Once a day at UTC midnight.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 FCAS scores returned (rounded up).</p>
	 * 
	 * @param start Optionally offset the start (1-based index) of the paginated list of items to return. Default: 1
	 * @param limit Optionally specify the number of results to return. Use this parameter and the "start" parameter to determine your own pagination size. Default: 100
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass point_change_24h,percent_change_24h to include all auxiliary fields. Default: "point_change_24h,percent_change_24h"
	 * 
	 * @return A paginated list of FCAS scores for all cryptocurrencies currently supported by FCAS.
	 */
	public ResponseEntity<String> getFlipsideCryptoFcasListingsLatest(Long start, Long limit, String aux) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("start", (start != null ? Long.toString(start) : null));
		paramMap.put("limit", (limit != null ? Long.toString(limit) : null));
		paramMap.put("aux", aux);
		
		return super.getResponseFromEndpoint(PARTNERS_FLIPSIDE_CRYPTO_FCAS_LISTINGS_LATEST_ENDPOINT, paramMap);
	}

	/**
	 * <p>Returns the latest FCAS score for 1 or more cryptocurrencies. FCAS ratings are on a 0-1000 point scale with a corresponding letter grade and is updated once a day at UTC midnight.</p>
	 * <p>FCAS stands for Fundamental Crypto Asset Score, a single, consistently comparable value for measuring cryptocurrency project health. FCAS measures User Activity, Developer Behavior and Market Maturity and is provided by FlipSide Crypto. Users interested in FCAS historical data including sub-component scoring may inquire through our CSV Data Delivery request form.</p>
	 * <p><b>Disclaimer:</b> Ratings that are calculated by third party organizations and are not influenced or endorsed by CoinMarketCap in any way.</p>
	 * 
	 * <p><b>NOTE: Use this endpoint to request the latest FCAS score for specific cryptocurrencies. If you require FCAS for all supported cryptocurrencies use /v1/partners/flipside-crypto/fcas/listings/latest which is optimized for that purpose. The response data between these endpoints is otherwise the same.</b></p>
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
	 * <p><b>Cache / Update frequency:</b> Once a day at UTC midnight.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 FCAS scores returned (rounded up).</p>
	 * 
	 * @param id One or more comma-separated cryptocurrency CoinMarketCap IDs. Example: 1,2
	 * @param slug Alternatively pass a comma-separated list of cryptocurrency slugs. Example: "bitcoin,ethereum"
	 * @param symbol Alternatively pass one or more comma-separated cryptocurrency symbols. Example: "BTC,ETH". At least one "id" or "slug" or "symbol" is required for this request.
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass point_change_24h,percent_change_24h to include all auxiliary fields. Default: "point_change_24h,percent_change_24h"
	 * 
	 * @return The latest FCAS score for 1 or more cryptocurrencies.
	 */
	public ResponseEntity<String> getFlipsideCryptoFcasQuotesLatest(String id, String slug, String symbol, String aux) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		paramMap.put("slug", slug);
		paramMap.put("symbol", symbol);
		paramMap.put("aux", aux);
		
		return super.getResponseFromEndpoint(PARTNERS_FLIPSIDE_CRYPTO_FCAS_QUOTES_LATEST_ENDPOINT, paramMap);
	}
}

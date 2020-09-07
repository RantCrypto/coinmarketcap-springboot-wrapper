/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rantcrypto.cmc.api.service.PartnersService;

/**
 * <p>API endpoints for managing your API key. This category currently includes 1 endpoint:</p>
 * <ul>
 * 	<li><b>/v1/partners/flipside-crypto/fcas/listings/latest -</b> List all available FCAS scores</li>
 * 	<li><b>/v1/partners/flipside-crypto/fcas/quotes/latest -</b> Request specific FCAS scores</li>
 * </ul>
 * 
 * <p><b>Disclaimer:</b> /partners/ content is offered by third party organizations and is not influenced or endorsed by CoinMarketCap.</p>
 * 
 * @author Phillip Groves
 */
@RestController
@RequestMapping(path = "v1/partners")
public class PartnersController {

	@Autowired
	private PartnersService partnersService;
	
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
	@GetMapping(path = "flipside-crypto/fcas/listings/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getFlipsideCryptoFcasListingsLatest(
			@RequestParam(required = false) Long start, 
			@RequestParam(required = false) Long limit,
			@RequestParam(required = false) String aux) {
		return this.partnersService.getFlipsideCryptoFcasListingsLatest(start, limit, aux);
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
	@GetMapping(path = "flipside-crypto/fcas/quotes/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getFlipsideCryptoFcasQuotesLatest(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String slug,
			@RequestParam(required = false) String symbol,
			@RequestParam(required = false) String aux) {
		return this.partnersService.getFlipsideCryptoFcasQuotesLatest(id, slug, symbol, aux);
	}
}

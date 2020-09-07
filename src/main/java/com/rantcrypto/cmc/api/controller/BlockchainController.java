/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rantcrypto.cmc.api.service.BlockchainService;

/**
 * <p>API endpoints for blockchain data. This category currently includes 1 endpoint:</p>
 * <ul>
 * 	<li><b>/v1/blockchain/statistics/latest -</b> Latest statistics</li>
 * </ul>
 * 
 * @author Phillip Groves
 */
@RestController
@RequestMapping(path = "v1/blockchain")
public class BlockchainController {

	@Autowired
	private BlockchainService blockchainService;
	
	/**
	 * <p>Returns the latest blockchain statistics data for 1 or more blockchains. Bitcoin, Litecoin, and Ethereum are currently supported. Additional blockchains will be made available on a regular basis.</p>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Enterprise</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Every 15 seconds.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per request.</p>
	 * 
	 * @param id One or more comma-separated cryptocurrency CoinMarketCap IDs to return blockchain data for. Pass 1,2,1027 to request all currently supported blockchains.
	 * @param symbol Alternatively pass one or more comma-separated cryptocurrency symbols. Pass BTC,LTC,ETH to request all currently supported blockchains.
	 * @param slug Alternatively pass a comma-separated list of cryptocurrency slugs. Pass bitcoin,litecoin,ethereum to request all currently supported blockchains.
	 * 
	 * @return Latest blockchain statistics data for 1 or more blockchains.
	 */
	@GetMapping(path = "statistics/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getStatisticsLatest(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String symbol,
			@RequestParam(required = false) String slug) {
		return this.blockchainService.getStatisticsLatest(id, symbol, slug);
	}
}

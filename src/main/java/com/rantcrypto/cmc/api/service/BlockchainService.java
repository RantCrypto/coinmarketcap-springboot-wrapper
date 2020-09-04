/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * <p>Service worker layer for {@link com.rantcrypto.cmc.api.controller.BlockchainController }</p>
 * 
 * @author Phillip Groves
 * @author CoinMarketCap (documentation / data)
 */
@Service
public class BlockchainService extends CoinMarketCapService {
	
	@Value("${com.rantcrypto.cmc.api.v1.blockchain.statistics-latest-endpoint:/v1/blockchain/statistics/latest}") 
	protected String BLOCKCHAIN_STATISTICS_LATEST_ENDPOINT;
	

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
	public ResponseEntity<String> getStatisticsLatest(String id, String symbol, String slug) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		paramMap.put("symbol", symbol);
		paramMap.put("slug", slug);
		
		return super.getResponseFromEndpoint(BLOCKCHAIN_STATISTICS_LATEST_ENDPOINT, paramMap);
	}
}

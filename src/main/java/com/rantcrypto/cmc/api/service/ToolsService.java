/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * <p>Service worker layer for {@link com.rantcrypto.cmc.api.controller.ToolsController }</p>
 * 
 * @author Phillip Groves
 * @author CoinMarketCap (documentation / data)
 */
@Service
public class ToolsService extends CoinMarketCapService {

	@Value("${com.rantcrypto.cmc.api.v1.tools.price-conversion-endpoint:/v1/tools/price-conversion}") 
	private String TOOLS_PRICE_CONVERSION_ENDPOINT;
	
	@Value("${com.rantcrypto.cmc.api.v1.tools.price-conversion-endpoint.cache-time:-1}") 
	private Long TOOLS_PRICE_CONVERSION_ENDPOINT_CACHE_TIME;
	
	
	/**
	 * <p>Convert an amount of one cryptocurrency or fiat currency into one or more different currencies utilizing the latest market rate for each currency. You may optionally pass a historical timestamp as time to convert values based on historical rates (as your API plan supports).</p>
	 * 
	 * <p><b>Technical Notes</b></p>
	 * <ul>
	 * 	<li>Latest market rate conversions are accurate to 1 minute of specificity. Historical conversions are accurate to 1 minute of specificity outside of non-USD fiat conversions which have 5 minute specificity.</li>
	 * 	<li>You may reference a current list of all supported cryptocurrencies via the cryptocurrency/map endpoint. This endpoint also returns the supported date ranges for historical conversions via the first_historical_data and last_historical_data properties.</li>
	 * 	<li>Conversions are supported in 93 different fiat currencies and 4 precious metals as outlined here. Historical fiat conversions are supported as far back as 2013-04-28.</li>
	 * 	<li>A last_updated timestamp is included for both your source currency and each conversion currency. This is the timestamp of the closest market rate record referenced for each currency during the conversion.</li>
	 * </ul>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Basic (Latest market price conversions)</li>
	 * 	<li>Hobbyist (Latest market price conversions + 1 month historical)</li>
	 * 	<li>Startup (Latest market price conversions + 1 month historical)</li>
	 * 	<li>Standard (Latest market price conversions + 3 months historical)</li>
	 * 	<li>Professional (Latest market price conversions + 12 months historical)</li>
	 * 	<li>Enterprise (Latest market price conversions + up to 6 years historical)</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Every 60 seconds for the lastest cryptocurrency and fiat currency rates.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per call and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param amount Required. An amount of currency to convert. Example: 10.43
	 * @param id The CoinMarketCap currency ID of the base cryptocurrency or fiat to convert from. Example: "1"
	 * @param symbol Alternatively the currency symbol of the base cryptocurrency or fiat to convert from. Example: "BTC". One "id" or "symbol" is required.
	 * @param time Optional timestamp (Unix or ISO 8601) to reference historical pricing during conversion. If not passed, the current time will be used. If passed, we'll reference the closest historic values available for this conversion.
	 * @param convert Pass up to 120 comma-separated fiat or cryptocurrency symbols to convert the source amount to.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * 
	 * @return Converted an amount of one cryptocurrency or fiat currency into one or more different currencies
	 */
	public ResponseEntity<Object> getPriceConversion(Double amount, String id, String symbol, String time, String convert,
			String convertId) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("amount", (amount != null ? Double.toString(amount) : null));
		paramMap.put("id", id);
		paramMap.put("symbol", symbol);
		paramMap.put("time", time);
		paramMap.put("convert", convert);
		paramMap.put("convert_id", convertId);
		
		return super.getResponseFromEndpoint(TOOLS_PRICE_CONVERSION_ENDPOINT, paramMap);
	}
}

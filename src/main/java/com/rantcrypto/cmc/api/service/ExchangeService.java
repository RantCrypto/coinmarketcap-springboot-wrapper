/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * <p>Service worker layer for {@link com.rantcrypto.cmc.api.controller.ExchangeController }</p>
 * 
 * @author Phillip Groves
 * @author CoinMarketCap (documentation / data)
 */
@Service
public class ExchangeService extends CoinMarketCapService {

	@Value("${com.rantcrypto.cmc.api.v1.exchange.map-endpoint:/v1/exchange/map}") 
	private String EXCHANGE_MAP_ENDPOINT;
	
	@Value("${com.rantcrypto.cmc.api.v1.exchange.info-endpoint:/v1/exchange/info}") 
	private String EXCHANGE_INFO_ENDPOINT;
	
	@Value("${com.rantcrypto.cmc.api.v1.exchange.listings-latest-endpoint:/v1/exchange/listings/latest}") 
	private String EXCHANGE_LISTINGS_LATEST_ENDPOINT;
	
	@Value("${com.rantcrypto.cmc.api.v1.exchange.listings-latest-endpoint:/v1/exchange/listings/historical}") 
	private String EXCHANGE_LISTINGS_HISTORICAL_ENDPOINT;
	
	@Value("${com.rantcrypto.cmc.api.v1.exchange.quotes-latest-endpoint:/v1/exchange/quotes/latest}") 
	private String EXCHANGE_QUOTES_LATEST_ENDPOINT;
	
	@Value("${com.rantcrypto.cmc.api.v1.exchange.quotes-historical-endpoint:/v1/exchange/quotes/historical}") 
	private String EXCHANGE_QUOTES_HISTORICAL_ENDPOINT;
	
	@Value("${com.rantcrypto.cmc.api.v1.exchange.market-pairs-latest-endpoint:/v1/exchange/market-pairs/latest}") 
	private String EXCHANGE_MARKET_PAIRS_LATEST_ENDPOINT;
	
	
	/**
	 * <p>Returns a paginated list of all active cryptocurrency exchanges by CoinMarketCap ID. We recommend using this convenience endpoint to lookup and utilize our unique exchange id across all endpoints as typical exchange identifiers may change over time. As a convenience you may pass a comma-separated list of exchanges by slug to filter this list to only those you require or the aux parameter to slim down the payload.</p>
	 * <p>By default this endpoint returns exchanges that have at least 1 actively tracked market. You may receive a map of all inactive cryptocurrencies by passing listing_status=inactive. You may also receive a map of registered exchanges that are listed but do not yet meet methodology requirements to have tracked markets available via listing_status=untracked. Please review (3) Listing Tiers in CoinMarketCap methodology documentation for additional details on listing states.</p>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Startup</li>
	 * 	<li>Standard</li>
	 * 	<li>Professional</li>
	 * 	<li>Enterprise</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Mapping data is updated only as needed, every 30 seconds.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per call.</p>
	 * 
	 * @param listingStatus Only active exchanges are returned by default. Pass inactive to get a list of exchanges that are no longer active. Pass untracked to get a list of exchanges that are registered but do not currently meet methodology requirements to have active markets tracked. You may pass one or more comma-separated values. Default: "active"
	 * @param slug Optionally pass a comma-separated list of exchange slugs (lowercase URL friendly shorthand name with spaces replaced with dashes) to return CoinMarketCap IDs for. If this option is passed, other options will be ignored.
	 * @param start Optionally offset the start (1-based index) of the paginated list of items to return. Default: 1
	 * @param limit Optionally specify the number of results to return. Use this parameter and the "start" parameter to determine your own pagination size.
	 * @param sort What field to sort the list of exchanges by. Default: "id". Valid values: "volume_24h", "id"
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass first_historical_data,last_historical_data,is_active,status to include all auxiliary fields. Default: "first_historical_data,last_historical_data,is_active"
	 * 
	 * @return A paginated list of all active cryptocurrency exchanges
	 */
	public ResponseEntity<String> getMap(String listingStatus, String slug, Long start, Long limit, String sort, String aux) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("listing_status", listingStatus);
		paramMap.put("slug", slug);
		paramMap.put("start", (start != null ? Long.toString(start) : null));
		paramMap.put("limit", (limit != null ? Long.toString(limit) : null));
		paramMap.put("sort", sort);
		paramMap.put("aux", aux);
		
		return super.getResponseFromEndpoint(EXCHANGE_MAP_ENDPOINT, paramMap);
	}

	/**
	 * <p>Returns all static metadata for one or more exchanges. This information includes details like launch date, logo, official website URL, social links, and market fee documentation URL.</p>
	 *
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Startup</li>
	 * 	<li>Standard</li>
	 * 	<li>Professional</li>
	 * 	<li>Enterprise</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Static data is updated only as needed, every 30 seconds.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 exchanges returned (rounded up).</p>
	 * 
	 * @param id One or more comma-separated CoinMarketCap cryptocurrency exchange ids. Example: "1,2"
	 * @param slug Alternatively, one or more comma-separated exchange names in URL friendly shorthand "slug" format (all lowercase, spaces replaced with hyphens). Example: "binance,gdax". At least one "id" or "slug" is required.
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass urls,logo,description,date_launched,notice,status to include all auxiliary fields. Default: "urls,logo,description,date_launched,notice"
	 * 
	 * @return All static metadata for one or more exchanges.
	 */
	public ResponseEntity<String> getInfo(String id, String slug, String aux) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		paramMap.put("slug", slug);
		paramMap.put("aux", aux);
		
		return super.getResponseFromEndpoint(EXCHANGE_INFO_ENDPOINT, paramMap);
	}

	/**
	 * <p>Returns a paginated list of all cryptocurrency exchanges including the latest aggregate market data for each exchange. Use the "convert" option to return market values in multiple fiat and cryptocurrency conversions in the same call.</p>
	 * <p><b>NOTE: Use this endpoint if you need a sorted and paginated list of exchanges. If you want to query for market data on a few specific exchanges use /v1/exchange/quotes/latest which is optimized for that purpose. The response data between these endpoints is otherwise the same.</b></p>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Standard</li>
	 * 	<li>Professional</li>
	 * 	<li>Enterprise</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Every 1 minute.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 exchanges returned (rounded up) and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param start Optionally offset the start (1-based index) of the paginated list of items to return. Default: 1
	 * @param limit Optionally specify the number of results to return. Use this parameter and the "start" parameter to determine your own pagination size. Default: 100
	 * @param sort What field to sort the list of exchanges by. Default: "volume_24h". Valid values: "name", "volume_24h", "volume_24h_adjusted"
	 * @param sortDir The direction in which to order exchanges against the specified sort. Valid values: "asc", "desc"
	 * @param marketType The type of exchange markets to include in rankings. This field is deprecated. Please use "all" for accurate sorting. Default: "all". Valid values: "fees", "no_fees", "all"
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass num_market_pairs,date_launched to include all auxiliary fields. Default: "num_market_pairs"
	 * @param convert Optionally calculate market quotes in up to 120 currencies at once by passing a comma-separated list of cryptocurrency or fiat currency symbols. Each additional convert option beyond the first requires an additional call credit. A list of supported fiat options can be found here. Each conversion is returned in its own "quote" object.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * 
	 * @return A paginated list of all cryptocurrency exchanges including the latest aggregate market data for each exchange.
	 */
	public ResponseEntity<String> getListingsLatest(Long start, Long limit, String sort, String sortDir, String marketType, String aux,
			String convert, String convertId) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("start", (start != null ? Long.toString(start) : null));
		paramMap.put("limit", (limit != null ? Long.toString(limit) : null));
		paramMap.put("sort", sort);
		paramMap.put("sort_dir", sortDir);
		paramMap.put("market_type", marketType);
		paramMap.put("aux", aux);
		paramMap.put("convert", convert);
		paramMap.put("convert_id", convertId);
		
		return super.getResponseFromEndpoint(EXCHANGE_LISTINGS_LATEST_ENDPOINT, paramMap);
	}

	/**
	 * <p><b>This endpoint is not yet available.</b></p>
	 * <p>Returns a paginated list of all cryptocurrency exchanges with historical market data for a given point in time. Use the "convert" option to return market values in multiple fiat and cryptocurrency conversions in the same call.</p>
	 * 
	 * @return A paginated list of all cryptocurrency exchanges with historical market data for a given point in time.
	 */
	public ResponseEntity<String> getListingsHistorical() {
		return super.getResponseFromEndpoint(EXCHANGE_LISTINGS_HISTORICAL_ENDPOINT);
	}

	/**
	 * <p>Returns the latest aggregate market data for 1 or more exchanges. Use the "convert" option to return market values in multiple fiat and cryptocurrency conversions in the same call.</p>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Standard</li>
	 * 	<li>Professional</li>
	 * 	<li>Enterprise</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Every 60 seconds.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 exchanges returned (rounded up) and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param id One or more comma-separated CoinMarketCap exchange IDs. Example: "1,2"
	 * @param slug Alternatively, pass a comma-separated list of exchange "slugs" (URL friendly all lowercase shorthand version of name with spaces replaced with hyphens). Example: "binance,gdax". At least one "id" or "slug" is required.
	 * @param convert Optionally calculate market quotes in up to 120 currencies at once by passing a comma-separated list of cryptocurrency or fiat currency symbols. Each additional convert option beyond the first requires an additional call credit. A list of supported fiat options can be found here. Each conversion is returned in its own "quote" object.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * 
	 * @return Latest aggregate market data for 1 or more exchanges.
	 */
	public ResponseEntity<String> getQuotesLatest(String id, String slug, String convert, String convertId) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		paramMap.put("slug", slug);
		paramMap.put("convert", convert);
		paramMap.put("convert_id", convertId);
		
		return super.getResponseFromEndpoint(EXCHANGE_QUOTES_LATEST_ENDPOINT, paramMap);
	}

	public ResponseEntity<String> getQuotesHistorical(String id, String slug, String timeStart, String timeEnd, Long count,
			String interval, String convert, String convertId) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		paramMap.put("slug", slug);
		paramMap.put("time_start", timeStart);
		paramMap.put("time_end", timeEnd);
		paramMap.put("count", (count != null ? Long.toString(count) : null));
		paramMap.put("interval", interval);
		paramMap.put("convert", convert);
		paramMap.put("convert_id", convertId);
		
		return super.getResponseFromEndpoint(EXCHANGE_QUOTES_HISTORICAL_ENDPOINT, paramMap);
	}

	/**
	 * <p>Returns all active market pairs that CoinMarketCap tracks for a given exchange. The latest price and volume information is returned for each market. Use the "convert" option to return market values in multiple fiat and cryptocurrency conversions in the same call.</p>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Standard</li>
	 * 	<li>Professional</li>
	 * 	<li>Enterprise</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Every 60 seconds.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 market pairs returned (rounded up) and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param id A CoinMarketCap exchange ID. Example: "1"
	 * @param slug Alternatively pass an exchange "slug" (URL friendly all lowercase shorthand version of name with spaces replaced with hyphens). Example: "binance". One "id" or "slug" is required.
	 * @param start Optionally offset the start (1-based index) of the paginated list of items to return. Default: 1
	 * @param limit Optionally specify the number of results to return. Use this parameter and the "start" parameter to determine your own pagination size. Default: 100
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass num_market_pairs,category,fee_type,market_url,currency_name,currency_slug,price_quote to include all auxiliary fields. Default: "num_market_pairs,category,fee_type"
	 * @param matchedId Optionally include one or more comma-delimited fiat or cryptocurrency IDs to filter market pairs by. For example ?matched_id=2781 would only return BTC markets that matched: "BTC/USD" or "USD/BTC" for the requested exchange. This parameter cannot be used when matched_symbol is used.
	 * @param matchedSymbol Optionally include one or more comma-delimited fiat or cryptocurrency symbols to filter market pairs by. For example ?matched_symbol=USD would only return BTC markets that matched: "BTC/USD" or "USD/BTC" for the requested exchange. This parameter cannot be used when matched_id is used.
	 * @param category The category of trading this market falls under. Spot markets are the most common but options include derivatives and OTC. Default: "all". Valid values: "all", "spot", "derivatives", "otc"
	 * @param feeType The fee type the exchange enforces for this market. Default: "all". Valid values: "all", "percentage", "no-fees", "transactional-mining", "unknown"
	 * @param convert Optionally calculate market quotes in up to 120 currencies at once by passing a comma-separated list of cryptocurrency or fiat currency symbols. Each additional convert option beyond the first requires an additional call credit. A list of supported fiat options can be found here. Each conversion is returned in its own "quote" object.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * 
	 * @return All active market pairs that CoinMarketCap tracks for a given exchange.
	 */
	public ResponseEntity<String> getMarketPairsLatest(String id, String slug, Long start, Long limit, String aux, String matchedId,
			String matchedSymbol, String category, String feeType, String convert, String convertId) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		paramMap.put("slug", slug);
		paramMap.put("start", (start != null ? Long.toString(start) : null));
		paramMap.put("limit", (limit != null ? Long.toString(limit) : null));
		paramMap.put("aux", aux);
		paramMap.put("matched_id", matchedId);
		paramMap.put("matched_symbol", matchedSymbol);
		paramMap.put("category", category);
		paramMap.put("fee_type", feeType);
		paramMap.put("convert", convert);
		paramMap.put("convert_id", convertId);
		
		return super.getResponseFromEndpoint(EXCHANGE_MARKET_PAIRS_LATEST_ENDPOINT, paramMap);
	}
}

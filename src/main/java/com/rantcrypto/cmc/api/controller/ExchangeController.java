/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rantcrypto.cmc.api.service.ExchangeService;

/**
 * <p>API endpoints for cryptocurrency exchanges. This category currently includes 7 endpoints:</p>
 * <ul>
 * 	<li><b>/v1/exchange/map -</b> CoinMarketCap ID map</li>
 * 	<li><b>/v1/exchange/info -</b> Metadata</li>
 * 	<li><b>/v1/exchange/listings/latest -</b> Latest listings</li>
 * 	<li><b>/v1/exchange/listings/historical -</b> Historical listings</li>
 * 	<li><b>/v1/exchange/quotes/latest -</b> Latest quotes</li>
 * 	<li><b>/v1/exchange/quotes/historical -</b> Historical quotes</li>
 * 	<li><b>/v1/exchange/market-pairs/latest -</b> Latest market pairs</li>
 * </ul>
 * 
 * @author Phillip Groves
 */
@RestController
@RequestMapping(path = "v1/exchange")
public class ExchangeController {

	@Autowired
	private ExchangeService exchangeService;
	
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
	 * @return Paginated list of all active cryptocurrency exchanges
	 */
	@GetMapping(path = "map", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMap(
			@RequestParam(required = false, name = "listing_status") String listingStatus, 
			@RequestParam(required = false) String slug,
			@RequestParam(required = false) Long start, 
			@RequestParam(required = false) Long limit,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String aux) {
		return this.exchangeService.getMap(listingStatus, slug, start, limit, sort, aux);
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
	@GetMapping(path = "info", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getInfo(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String slug,
			@RequestParam(required = false) String aux) {
		return this.exchangeService.getInfo(id, slug, aux);
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
	 * @return Paginated list of all cryptocurrency exchanges including the latest aggregate market data for each exchange.
	 */
	@GetMapping(path = "listings/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getListingsLatest(
			@RequestParam(required = false) Long start, 
			@RequestParam(required = false) Long limit, 
			@RequestParam(required = false) String sort, 
			@RequestParam(required = false, name = "sort_dir") String sortDir, 
			@RequestParam(required = false, name = "market_type") String marketType, 
			@RequestParam(required = false) String aux,
			@RequestParam(required = false) String convert, 
			@RequestParam(required = false, name = "convert_id") String convertId) {
		return this.exchangeService.getListingsLatest(start, limit, sort, sortDir, marketType, aux, convert, convertId);
	}
	
	/**
	 * <p><b>This endpoint is not yet available.</b></p>
	 * <p>Returns a paginated list of all cryptocurrency exchanges with historical market data for a given point in time. Use the "convert" option to return market values in multiple fiat and cryptocurrency conversions in the same call.</p>
	 * 
	 * @return Paginated list of all cryptocurrency exchanges with historical market data for a given point in time.
	 */
	@GetMapping(path = "listings/historical", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getListingsHistorical() {
		return this.exchangeService.getListingsHistorical();
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
	@GetMapping(path = "quotes/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getQuotesLatest(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String slug, 
			@RequestParam(required = false) String convert, 
			@RequestParam(required = false, name = "convert_id") String convertId) {
		return this.exchangeService.getQuotesLatest(id, slug, convert, convertId);
	}
	
	/**
	 * <p>Returns an interval of historic quotes for any exchange based on time and interval parameters.</p>
	 * <p><b>Note: You may use the /exchange/map endpoint to receive a list of earliest historical dates that may be fetched for each exchange as first_historical_data. This timestamp will either be the date CoinMarketCap first started tracking the exchange or 2018-04-26T00:45:00.000Z, the earliest date this type of historical data is available for.</b></p>
	 * 
	 * <p><b>Technical Notes</b></p>
	 * <ul>
	 * 	<li>A historic quote for every "interval" period between your "time_start" and "time_end" will be returned.</li>
	 * 	<li>If a "time_start" is not supplied, the "interval" will be applied in reverse from "time_end".</li>
	 *  <li>If "time_end" is not supplied, it defaults to the current time.</li>
	 *  <li>At each "interval" period, the historic quote that is closest in time to the requested time will be returned.</li>
	 *  <li>If no historic quotes are available in a given "interval" period up until the next interval period, it will be skipped.</li>
	 *  <li>This endpoint supports requesting multiple exchanges in the same call. Please note the API response will be wrapped in an additional object in this case.</li>
	 * </ul>
	 * 
	 * <p><b>Interval Options</b></p>
	 * <p>There are 2 types of time interval formats that may be used for "interval".</p>
	 * 
	 * <p>The first are calendar year and time constants in UTC time:</p>
	 * <ul>
	 * 	<li><b>"hourly" -</b> Get the first quote available at the beginning of each calendar hour.</li>
	 * 	<li><b>"daily" -</b> Get the first quote available at the beginning of each calendar day.</li>
	 * 	<li><b>"weekly" -</b> Get the first quote available at the beginning of each calendar week.</li>
	 * 	<li><b>"monthly" -</b> Get the first quote available at the beginning of each calendar month.</li>
	 * 	<li><b>"yearly" -</b> Get the first quote available at the beginning of each calendar year.</li>
	 * </ul>
	 * 
	 * <p>The second are relative time intervals.</p>
	 * <ul>
	 * 	<li><b>"m": </b> Get the first quote available every "m" minutes (60 second intervals). Supported minutes are: "5m", "10m", "15m", "30m", "45m".</li>
	 * 	<li><b>"h": </b> Get the first quote available every "h" hours (3600 second intervals). Supported hour intervals are: "1h", "2h", "3h", "4h", "6h", "12h".</li>
	 * 	<li><b>"d": </b> Get the first quote available every "d" days (86400 second intervals). Supported day intervals are: "1d", "2d", "3d", "7d", "14d", "15d", "30d", "60d", "90d", "365d".</li>
	 * </ul>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Standard (3 months)</li>
	 * 	<li>Professional (Up to 12 months)</li>
	 * 	<li>Enterprise (Up to 6 years)</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Every 5 minutes.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 historical data points returned (rounded up) and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param id One or more comma-separated exchange CoinMarketCap ids. Example: "24,270"
	 * @param slug Alternatively, one or more comma-separated exchange names in URL friendly shorthand "slug" format (all lowercase, spaces replaced with hyphens). Example: "binance,kraken". At least one "id" or "slug" is required.
	 * @param timeStart Timestamp (Unix or ISO 8601) to start returning quotes for. Optional, if not passed, we'll return quotes calculated in reverse from "time_end".
	 * @param timeEnd Timestamp (Unix or ISO 8601) to stop returning quotes for (inclusive). Optional, if not passed, we'll default to the current time. If no "time_start" is passed, we return quotes in reverse order starting from this time.
	 * @param count The number of interval periods to return results for. Optional, required if both "time_start" and "time_end" aren't supplied. The default is 10 items. The current query limit is 10000.
	 * @param interval Interval of time to return data points for. See details in endpoint description. Default: "5m". Valid values: "yearly", "monthly", "weekly", "daily", "hourly", "5m", "10m", "15m", "30m", "45m", "1h", "2h", "3h", "4h", "6h", "12h", "24h", "1d", "2d", "3d", "7d", "14d", "15d", "30d", "60d", "90d", "365d"
	 * @param convert By default market quotes are returned in USD. Optionally calculate market quotes in up to 3 other fiat currencies or cryptocurrencies.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * 
	 * @return Interval of historic quotes for any exchange.
	 */
	@GetMapping(path = "quotes/historical", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getQuotesHistorical(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String slug, 
			@RequestParam(required = false, name = "time_start") String timeStart,
			@RequestParam(required = false, name = "time_end") String timeEnd,
			@RequestParam(required = false) Long count, 
			@RequestParam(required = false) String interval, 
			@RequestParam(required = false) String convert, 
			@RequestParam(required = false, name = "convert_id") String convertId) {
		return this.exchangeService.getQuotesHistorical(id, slug, timeStart, timeEnd, count, interval, convert, convertId);
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
	@GetMapping(path = "market-pairs/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMarketPairsLatest(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String slug, 
			@RequestParam(required = false) Long start, 
			@RequestParam(required = false) Long limit, 
			@RequestParam(required = false) String aux, 
			@RequestParam(required = false, name = "matched_id") String matchedId, 
			@RequestParam(required = false, name = "matched_symbol") String matchedSymbol, 
			@RequestParam(required = false) String category, 
			@RequestParam(required = false, name = "fee_type") String feeType, 
			@RequestParam(required = false) String convert, 
			@RequestParam(required = false, name = "convert_id") String convertId) {
		return this.exchangeService.getMarketPairsLatest(
				id, slug, start, limit, aux, matchedId, matchedSymbol, 
				category, feeType, convert, convertId);
	}
}

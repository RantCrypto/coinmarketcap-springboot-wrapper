/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rantcrypto.cmc.api.service.CryptocurrencyService;

/**
 * <p>API endpoints for cryptocurrencies. This category currently includes 10 endpoints:</p>
 * <ul>
 * 	<li><b>/v1/cryptocurrency/map -</b> CoinMarketCap ID map</li>
 * 	<li><b>/v1/cryptocurrency/info -</b> Metadata</li>
 * 	<li><b>/v1/cryptocurrency/listings/latest -</b> Latest listings</li>
 * 	<li><b>/v1/cryptocurrency/listings/historical -</b> Historical listings</li>
 * 	<li><b>/v1/cryptocurrency/quotes/latest -</b> Latest quotes</li>
 * 	<li><b>/v1/cryptocurrency/quotes/historical -</b> Historical quotes</li>
 * 	<li><b>/v1/cryptocurrency/market-pairs/latest -</b> Latest market pairs</li>
 * 	<li><b>/v1/cryptocurrency/ohlcv/latest -</b> Latest OHLCV</li>
 * 	<li><b>/v1/cryptocurrency/ohlcv/historical -</b> Historical OHLCV</li>
 * 	<li><b>/v1/cryptocurrency/price-performance-stats/latest -</b> Price performance Stats</li>
 * </ul>
 * 
 * @author Phillip Groves
 */
@RestController
@RequestMapping(path = "v1/cryptocurrency")
public class CryptocurrencyController {

	@Autowired
	private CryptocurrencyService cryptocurrencyService;
	
	/**
	 * <p>Returns a mapping of all cryptocurrencies to unique CoinMarketCap ids. Per CoinMarketCap Best Practices we recommend utilizing CMC ID instead of cryptocurrency symbols to securely identify cryptocurrencies with our other endpoints and in your own application logic. Each cryptocurrency returned includes typical identifiers such as name, symbol, and token_address for flexible mapping to id.</p>
	 * <p>By default this endpoint returns cryptocurrencies that have actively tracked markets on supported exchanges. You may receive a map of all inactive cryptocurrencies by passing listing_status=inactive. You may also receive a map of registered cryptocurrency projects that are listed but do not yet meet methodology requirements to have tracked markets via listing_status=untracked. Please review CoinMarketCap methodology documentation for additional details on listing states.</p>
	 * <p>Cryptocurrencies returned include first_historical_data and last_historical_data timestamps to conveniently reference historical date ranges available to query with historical time-series data endpoints. You may also use the aux parameter to only include properties you require to slim down the payload if calling this endpoint frequently.</p>
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
	 * @param listingStatus Only active cryptocurrencies are returned by default. Pass inactive to get a list of cryptocurrencies that are no longer active. Pass untracked to get a list of cryptocurrencies that are listed but do not yet meet methodology requirements to have tracked markets available. You may pass one or more comma-separated values. Default: "active"
	 * @param start Optionally offset the start (1-based index) of the paginated list of items to return.
	 * @param limit Optionally specify the number of results to return. Use this parameter and the "start" parameter to determine your own pagination size.
	 * @param sort What field to sort the list of cryptocurrencies by. Default: "id". Valid values: "cmc_rank", "id"
	 * @param symbol Optionally pass a comma-separated list of cryptocurrency symbols to return CoinMarketCap IDs for. If this option is passed, other options will be ignored.
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass platform, first_historical_data, last_historical_data, is_active, status to include all auxiliary fields. Default: platform, first_historical_data, last_historical_data, is_active
	 * 
	 * @return Mapping of all cryptocurrencies to unique CoinMarketCap ids
	 */
	@GetMapping(path = "map", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMap(
			@RequestParam(required = false, name = "listing_status") String listingStatus, 
			@RequestParam(required = false) Long start, 
			@RequestParam(required = false) Long limit,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String symbol,
			@RequestParam(required = false) String aux) {
		return this.cryptocurrencyService.getMap(listingStatus, start, limit, sort, symbol, aux);
	}
	
	/**
	 * <p>Returns all static metadata available for one or more cryptocurrencies. This information includes details like logo, description, official website URL, social links, and links to a cryptocurrency's technical documentation.</p>
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
	 * <p><b>Cache / Update frequency:</b> Static data is updated only as needed, every 30 seconds.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 cryptocurrencies returned (rounded up).</p>
	 * 
	 * @param id One or more comma-separated CoinMarketCap cryptocurrency IDs. Example: "1,2"
	 * @param slug Alternatively pass a comma-separated list of cryptocurrency slugs. Example: "bitcoin,ethereum"
	 * @param symbol Alternatively pass one or more comma-separated cryptocurrency symbols. Example: "BTC,ETH". At least one "id" or "slug" or "symbol" is required for this request.
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Default: urls, logo, description, tags, platform, date_added, notice. Pass urls, logo, description, tags, platform, date_added, notice, status to include all auxiliary fields.
	 * 
	 * @return All static metadata available for one or more cryptocurrencies.
	 */
	@GetMapping(path = "info", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getInfo(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String slug,
			@RequestParam(required = false) String symbol,
			@RequestParam(required = false) String aux) {
		return this.cryptocurrencyService.getInfo(id, slug, symbol, aux);
	}
	
	/**
	 * <p>Returns a paginated list of all active cryptocurrencies with latest market data. The default "market_cap" sort returns cryptocurrency in order of CoinMarketCap's market cap rank (as outlined in our methodology) but you may configure this call to order by another market ranking field. Use the "convert" option to return market values in multiple fiat and cryptocurrency conversions in the same call.</p>
	 * <p><b>NOTE: Use this endpoint if you need a sorted and paginated list of all cryptocurrencies. If you want to query for market data on a few specific cryptocurrencies use /v1/cryptocurrency/quotes/latest which is optimized for that purpose. The response data between these endpoints is otherwise the same.</b></p>
	 * 
	 * <p>You may sort against any of the following:</p>
	 * <ul>
	 * 	<li><b>market_cap:</b> CoinMarketCap's market cap rank as outlined in CoinMarketCap methodology.</li>
	 * 	<li><b>market_cap_strict:</b> A strict market cap sort (latest trade price x circulating supply).</li>
	 * 	<li><b>name:</b> The cryptocurrency name.</li>
	 * 	<li><b>symbol:</b> The cryptocurrency symbol.</li>
	 * 	<li><b>date_added:</b> Date cryptocurrency was added to the system.</li>
	 * 	<li><b>price:</b> latest average trade price across markets.</li>
	 * 	<li><b>circulating_supply:</b> approximate number of coins currently in circulation.</li>
	 * 	<li><b>total_supply:</b> approximate total amount of coins in existence right now (minus any coins that have been verifiably burned).</li>
	 * 	<li><b>max_supply:</b> our best approximation of the maximum amount of coins that will ever exist in the lifetime of the currency.</li>
	 * 	<li><b>num_market_pairs:</b> number of market pairs across all exchanges trading each currency.</li>
	 * 	<li><b>market_cap_by_total_supply_strict:</b> market cap by total supply.</li>
	 * 	<li><b>volume_24h:</b> rolling 24 hour adjusted trading volume.</li>
	 * 	<li><b>volume_7d:</b> rolling 24 hour adjusted trading volume.</li>
	 * 	<li><b>volume_30d:</b> rolling 24 hour adjusted trading volume.</li>
	 * 	<li><b>percent_change_1h:</b> 1 hour trading price percentage change for each currency.</li>
	 * 	<li><b>percent_change_24h:</b> 24 hour trading price percentage change for each currency.</li>
	 * 	<li><b>percent_change_7d:</b> 7 day trading price percentage change for each currency.</li>
	 * </ul>
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
	 * <p><b>Cache / Update frequency:</b> Every 60 seconds.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 200 cryptocurrencies returned (rounded up) and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param start Optionally offset the start (1-based index) of the paginated list of items to return.
	 * @param limit Optionally specify the number of results to return. Use this parameter and the "start" parameter to determine your own pagination size.
	 * @param priceMin Optionally specify a threshold of minimum USD price to filter results by.
	 * @param priceMax Optionally specify a threshold of maximum USD price to filter results by.
	 * @param marketCapMin Optionally specify a threshold of minimum market cap to filter results by.
	 * @param marketCapMax Optionally specify a threshold of maximum market cap to filter results by.
	 * @param volume24hMin Optionally specify a threshold of minimum 24 hour USD volume to filter results by.
	 * @param volume24hMax Optionally specify a threshold of maximum 24 hour USD volume to filter results by.
	 * @param circulatingSupplyMin Optionally specify a threshold of minimum circulating supply to filter results by.
	 * @param circulatingSupplyMax Optionally specify a threshold of maximum circulating supply to filter results by.
	 * @param percentChange24hMin Optionally specify a threshold of minimum 24 hour percent change to filter results by.
	 * @param percentChange24hMax Optionally specify a threshold of maximum 24 hour percent change to filter results by.
	 * @param convert Optionally calculate market quotes in up to 120 currencies at once by passing a comma-separated list of cryptocurrency or fiat currency symbols. Each additional convert option beyond the first requires an additional call credit. A list of supported fiat options can be found here. Each conversion is returned in its own "quote" object.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * @param sort What field to sort the list of cryptocurrencies by. Default: "market_cap". Valid values: "name", "symbol", "date_added", "market_cap", "market_cap_strict", "price", "circulating_supply", "total_supply", "max_supply", "num_market_pairs", "volume_24h", "percent_change_1h", "percent_change_24h", "percent_change_7d", "market_cap_by_total_supply_strict", "volume_7d", "volume_30d"
	 * @param sortDir The direction in which to order cryptocurrencies against the specified sort. Valid values: "asc", "desc"
	 * @param cryptocurrencyType The type of cryptocurrency to include. Default: "all". Valid values: "all", "coins", "tokens"
	 * @param tag The tag of cryptocurrency to include. Default: "all". Valid values: "all", "defi"
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass num_market_pairs, cmc_rank, date_added, tags, platform, max_supply, circulating_supply, total_supply, market_cap_by_total_supply, volume_24h_reported, volume_7d, volume_7d_reported, volume_30d, volume_30d_reported, is_market_cap_included_in_calc to include all auxiliary fields. Default: "num_market_pairs, cmc_rank, date_added, tags, platform, max_supply, circulating_supply, total_supply"
	 * 
	 * @return Paginated list of all active cryptocurrencies with latest market data.
	 */
	@GetMapping(path = "listings/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getListingsLatest(
			@RequestParam(required = false) Long start, 
			@RequestParam(required = false) Long limit, 
			@RequestParam(required = false, name = "price_min") Double priceMin, 
			@RequestParam(required = false, name = "price_max") Double priceMax,
			@RequestParam(required = false, name = "market_cap_min") Double marketCapMin, 
			@RequestParam(required = false, name = "market_cap_max") Double marketCapMax,
			@RequestParam(required = false, name = "volume_24h_min") Double volume24hMin, 
			@RequestParam(required = false, name = "volume_24h_max") Double volume24hMax,
			@RequestParam(required = false, name = "circulating_supply_min") Double circulatingSupplyMin, 
			@RequestParam(required = false, name = "circulating_supply_max") Double circulatingSupplyMax,
			@RequestParam(required = false, name = "percent_change_24h_min") Double percentChange24hMin, 
			@RequestParam(required = false, name = "percent_change_24h_max") Double percentChange24hMax,
			@RequestParam(required = false) String convert, 
			@RequestParam(required = false, name = "convert_id") String convertId,
			@RequestParam(required = false) String sort, 
			@RequestParam(required = false, name = "sort_dir") String sortDir,
			@RequestParam(required = false, name = "cryptocurrency_type") String cryptocurrencyType, 
			@RequestParam(required = false) String tag,
			@RequestParam(required = false) String aux) {
		return this.cryptocurrencyService.getListingsLatest(
				start, limit, priceMin, priceMax, marketCapMin, marketCapMax, 
				volume24hMin, volume24hMax, circulatingSupplyMin, circulatingSupplyMax, 
				percentChange24hMin, percentChange24hMax, convert, convertId,
				sort, sortDir, cryptocurrencyType, tag, aux);
	}
	
	/**
	 * <p>Returns a ranked and sorted list of all cryptocurrencies for a historical UTC date.</p>
	 * 
	 * <p><b>Technical Notes</b></p>
	 * <ul>
	 * 	<li>This endpoint is identical in format to our /cryptocurrency/listings/latest endpoint but is used to retrieve historical daily ranking snapshots from the end of each UTC day.</li>
	 * 	<li>Daily snapshots reflect market data at the end of each UTC day and may be requested as far back as 2013-04-28 (as supported by your plan's historical limits).</li>
	 * 	<li>The required "date" parameter can be passed as a Unix timestamp or ISO 8601 date but only the date portion of the timestamp will be referenced. It is recommended to send an ISO date format like "2019-10-10" without time.</li>
	 * </ul>
	 * 
	 * <p>Cryptocurrencies are listed by cmc_rank by default. You may optionally sort against any of the following:</p>
	 * <ul>
	 * 	<li><b>cmc_rank:</b> CoinMarketCap's market cap rank as outlined in CoinMarketCap methodology.</li>
	 * 	<li><b>name:</b> The cryptocurrency name.</li>
	 * 	<li><b>symbol:</b> The cryptocurrency symbol.</li>
	 * 	<li><b>date_added:</b> Date cryptocurrency was added to the system.</li>
	 * 	<li><b>market_cap:</b> market cap (latest trade price x circulating supply).</li>
	 * 	<li><b>price:</b> latest average trade price across markets.</li>
	 * 	<li><b>circulating_supply:</b> approximate number of coins currently in circulation.</li>
	 * 	<li><b>total_supply:</b> approximate total amount of coins in existence right now (minus any coins that have been verifiably burned).</li>
	 * 	<li><b>max_supply:</b> our best approximation of the maximum amount of coins that will ever exist in the lifetime of the currency.</li>
	 * 	<li><b>num_market_pairs:</b> number of market pairs across all exchanges trading each currency.</li>
	 * 	<li><b>volume_24h:</b> 24 hour trading volume for each currency.</li>
	 * 	<li><b>percent_change_1h:</b> 1 hour trading price percentage change for each currency.</li>
	 * 	<li><b>percent_change_24h:</b> 24 hour trading price percentage change for each currency.</li>
	 * 	<li><b>percent_change_7d:</b> 7 day trading price percentage change for each currency.</li>
	 * </ul>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Standard (3 months)</li>
	 * 	<li>Professional (12 months)</li>
	 * 	<li>Enterprise (up to 6 years)</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> The last completed UTC day is available 30 minutes after midnight on the next UTC day.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 cryptocurrencies returned (rounded up) and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param date Required. date (Unix or ISO 8601) to reference day of snapshot.
	 * @param start Optionally offset the start (1-based index) of the paginated list of items to return.
	 * @param limit Optionally specify the number of results to return. Use this parameter and the "start" parameter to determine your own pagination size.
	 * @param convert Optionally calculate market quotes in up to 120 currencies at once by passing a comma-separated list of cryptocurrency or fiat currency symbols. Each additional convert option beyond the first requires an additional call credit. A list of supported fiat options can be found here. Each conversion is returned in its own "quote" object.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * @param sort What field to sort the list of cryptocurrencies by. Default: "cmc_rank". Valid values: "name", "symbol", "date_added", "market_cap", "market_cap_strict", "price", "circulating_supply", "total_supply", "max_supply", "num_market_pairs", "volume_24h", "percent_change_1h", "percent_change_24h", "percent_change_7d", "market_cap_by_total_supply_strict", "volume_7d", "volume_30d"
	 * @param sortDir The direction in which to order cryptocurrencies against the specified sort. Valid values: "asc", "desc"
	 * @param cryptocurrencyType The type of cryptocurrency to include. Default: "all". Valid values: "all", "coins", "tokens"
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass num_market_pairs, cmc_rank, date_added, tags, platform, max_supply, circulating_supply, total_supply, market_cap_by_total_supply, volume_24h_reported, volume_7d, volume_7d_reported, volume_30d, volume_30d_reported, is_market_cap_included_in_calc to include all auxiliary fields. Default: "num_market_pairs, cmc_rank, date_added, tags, platform, max_supply, circulating_supply, total_supply"
	 * 
	 * @return Ranked and sorted list of all cryptocurrencies for a historical UTC date.
	 */
	@GetMapping(path = "listings/historical", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getListingsHistorical(
			@RequestParam(required = false) String date, 
			@RequestParam(required = false) Long start, 
			@RequestParam(required = false) Long limit, 
			@RequestParam(required = false) String convert, 
			@RequestParam(required = false, name = "convert_id") String convertId,
			@RequestParam(required = false) String sort, 
			@RequestParam(required = false, name = "sort_dir") String sortDir,
			@RequestParam(required = false, name = "cryptocurrency_type") String cryptocurrencyType, 
			@RequestParam(required = false) String aux) {
		return this.cryptocurrencyService.getListingsHistorical(
				date, start, limit, convert, convertId, sort, sortDir, cryptocurrencyType, aux);
	}
	
	/**
	 * <p>Returns the latest market quote for 1 or more cryptocurrencies. Use the "convert" option to return market values in multiple fiat and cryptocurrency conversions in the same call.</p>
	 * <p><b>NOTE: Use this endpoint to request the latest quote for specific cryptocurrencies. If you need to request all cryptocurrencies use /v1/cryptocurrency/listings/latest which is optimized for that purpose. The response data between these endpoints is otherwise the same.</b></p>
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
	 * <p><b>Cache / Update frequency:</b> Every 60 seconds.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 cryptocurrencies returned (rounded up) and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param id One or more comma-separated cryptocurrency CoinMarketCap IDs. Example: 1,2
	 * @param slug Alternatively pass a comma-separated list of cryptocurrency slugs. Example: "bitcoin,ethereum"
	 * @param symbol Alternatively pass one or more comma-separated cryptocurrency symbols. Example: "BTC,ETH". At least one "id" or "slug" or "symbol" is required for this request.
	 * @param convert Optionally calculate market quotes in up to 120 currencies at once by passing a comma-separated list of cryptocurrency or fiat currency symbols. Each additional convert option beyond the first requires an additional call credit. A list of supported fiat options can be found here. Each conversion is returned in its own "quote" object.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass num_market_pairs,cmc_rank,date_added,tags,platform,max_supply,circulating_supply,total_supply,market_cap_by_total_supply,volume_24h_reported,volume_7d,volume_7d_reported,volume_30d,volume_30d_reported,is_active,is_fiat to include all auxiliary fields. Default: "num_market_pairs,cmc_rank,date_added,tags,platform,max_supply,circulating_supply,total_supply,is_active,is_fiat"
	 * @param skipInvalid Pass true to relax request validation rules. When requesting records on multiple cryptocurrencies an error is returned if no match is found for 1 or more requested cryptocurrencies. If set to true, invalid lookups will be skipped allowing valid cryptocurrencies to still be returned. Default: false
	 * 
	 * @return Latest market quote for 1 or more cryptocurrencies.
	 */
	@GetMapping(path = "quotes/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getQuotesLatest(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String slug, 
			@RequestParam(required = false) String symbol, 
			@RequestParam(required = false) String convert, 
			@RequestParam(required = false, name = "convert_id") String convertId,
			@RequestParam(required = false) String aux,
			@RequestParam(required = false, name = "skip_invalid") Boolean skipInvalid) {
		return this.cryptocurrencyService.getQuotesLatest(id, slug, symbol, convert, convertId, aux, skipInvalid);
	}
	
	/**
	 * <p>Returns an interval of historic market quotes for any cryptocurrency based on time and interval parameters.</p>
	 * 
	 * <p><b>Technical Notes</b></p>
	 * <ul>
	 * 	<li>A historic quote for every "interval" period between your "time_start" and "time_end" will be returned.</li>
	 * 	<li>If a "time_start" is not supplied, the "interval" will be applied in reverse from "time_end".</li>
	 *  <li>If "time_end" is not supplied, it defaults to the current time.</li>
	 *  <li>At each "interval" period, the historic quote that is closest in time to the requested time will be returned.</li>
	 *  <li>If no historic quotes are available in a given "interval" period up until the next interval period, it will be skipped.</li>
	 * </ul>
	 * 
	 * <p><b>Implementation Tips</b></p>
	 * <ul>
	 * 	<li>Want to get the last quote of each UTC day? Don't use "interval=daily" as that returns the first quote. Instead use "interval=24h" to repeat a specific timestamp search every 24 hours and pass ex. "time_start=2019-01-04T23:59:00.000Z" to query for the last record of each UTC day.</li>
	 * 	<li>This endpoint supports requesting multiple cryptocurrencies in the same call. Please note the API response will be wrapped in an additional object in this case.</li>
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
	 * 	<li>Standard</li>
	 * 	<li>Professional</li>
	 * 	<li>Enterprise</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Every 5 minutes.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 historical data points returned (rounded up) and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param id One or more comma-separated CoinMarketCap cryptocurrency IDs. Example: "1,2"
	 * @param symbol Alternatively pass one or more comma-separated cryptocurrency symbols. Example: "BTC,ETH". At least one "id" or "symbol" is required for this request.
	 * @param timeStart Timestamp (Unix or ISO 8601) to start returning quotes for. Optional, if not passed, we'll return quotes calculated in reverse from "time_end".
	 * @param timeEnd Timestamp (Unix or ISO 8601) to stop returning quotes for (inclusive). Optional, if not passed, we'll default to the current time. If no "time_start" is passed, we return quotes in reverse order starting from this time.
	 * @param count The number of interval periods to return results for. Optional, required if both "time_start" and "time_end" aren't supplied. The default is 10 items. The current query limit is 10000.
	 * @param interval Interval of time to return data points for. See details in endpoint description. Default: "5m". Valid values: "yearly", "monthly", "weekly", "daily", "hourly", "5m", "10m", "15m", "30m", "45m", "1h", "2h", "3h", "4h", "6h", "12h", "24h", "1d", "2d", "3d", "7d", "14d", "15d", "30d", "60d", "90d", "365d"
	 * @param convert By default market quotes are returned in USD. Optionally calculate market quotes in up to 3 other fiat currencies or cryptocurrencies.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass price,volume,market_cap,quote_timestamp,is_active,is_fiat,search_interval to include all auxiliary fields. Default: "price,volume,market_cap,quote_timestamp,is_active,is_fiat"
	 * 
	 * @return Interval of historic market quotes for any cryptocurrency.
	 */
	@GetMapping(path = "quotes/historical", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getQuotesHistorical(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String symbol, 
			@RequestParam(required = false, name = "time_start") String timeStart,
			@RequestParam(required = false, name = "time_end") String timeEnd,
			@RequestParam(required = false) Long count, 
			@RequestParam(required = false) String interval, 
			@RequestParam(required = false) String convert, 
			@RequestParam(required = false, name = "convert_id") String convertId,
			@RequestParam(required = false) String aux) {
		return this.cryptocurrencyService.getQuotesHistorical(
				id, symbol, timeStart, timeEnd, count, interval, convert, convertId, aux);
	}
	
	/**
	 * <p>Lists all active market pairs that CoinMarketCap tracks for a given cryptocurrency or fiat currency. All markets with this currency as the pair base or pair quote will be returned. The latest price and volume information is returned for each market. Use the "convert" option to return market values in multiple fiat and cryptocurrency conversions in the same call.</p>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Standard</li>
	 * 	<li>Professional</li>
	 * 	<li>Enterprise</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Every 1 minute.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 market pairs returned (rounded up) and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param id A cryptocurrency or fiat currency by CoinMarketCap ID to list market pairs for. Example: "1"
	 * @param slug Alternatively pass a cryptocurrency by slug. Example: "bitcoin"
	 * @param symbol Alternatively pass a cryptocurrency by symbol. Fiat currencies are not supported by this field. Example: "BTC". A single cryptocurrency "id", "slug", or "symbol" is required.
	 * @param start Optionally offset the start (1-based index) of the paginated list of items to return. Default: 1
	 * @param limit Optionally specify the number of results to return. Use this parameter and the "start" parameter to determine your own pagination size. Default: 100
	 * @param sortDir Optionally specify the sort direction of markets returned. Default: "desc". Valid values: "asc", "desc"
	 * @param sort Optionally specify the sort order of markets returned. By default we return a strict sort on 24 hour reported volume. Pass cmc_rank to return a CMC methodology based sort where markets with excluded volumes are returned last. Default: "volume_24h_strict". Valid values: "volume_24h_strict", "cmc_rank", "effective_liquidity", "market_score", "market_reputation"
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass num_market_pairs,category,fee_type,market_url,currency_name,currency_slug,price_quote,notice to include all auxiliary fields. Default: "num_market_pairs,category,fee_type"
	 * @param matchedId Optionally include one or more fiat or cryptocurrency IDs to filter market pairs by. For example ?id=1&amp;matched_id=2781 would only return BTC markets that matched: "BTC/USD" or "USD/BTC". This parameter cannot be used when matched_symbol is used.
	 * @param matchedSymbol Optionally include one or more fiat or cryptocurrency symbols to filter market pairs by. For example ?symbol=BTC&amp;matched_symbol=USD would only return BTC markets that matched: "BTC/USD" or "USD/BTC". This parameter cannot be used when matched_id is used.
	 * @param category The category of trading this market falls under. Spot markets are the most common but options include derivatives and OTC. Default: "all". Valid values: "all", "spot", "derivatives", "otc"
	 * @param feeType The fee type the exchange enforces for this market. Default: "all". Valid values: "all", "percentage", "no-fees", "transactional-mining", "unknown"
	 * @param convert Optionally calculate market quotes in up to 120 currencies at once by passing a comma-separated list of cryptocurrency or fiat currency symbols. Each additional convert option beyond the first requires an additional call credit. A list of supported fiat options can be found here. Each conversion is returned in its own "quote" object.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * 
	 * @return All active market pairs that CoinMarketCap tracks for a given cryptocurrency or fiat currency.
	 */
	@GetMapping(path = "market-pairs/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMarketPairsLatest(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String slug, 
			@RequestParam(required = false) String symbol, 
			@RequestParam(required = false) Long start,
			@RequestParam(required = false) Long limit,
			@RequestParam(required = false, name = "sort_dir") String sortDir, 
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String aux,
			@RequestParam(required = false, name = "matched_id") String matchedId, 
			@RequestParam(required = false, name = "matched_symbol") String matchedSymbol, 
			@RequestParam(required = false) String category,
			@RequestParam(required = false, name = "fee_type") String feeType, 
			@RequestParam(required = false) String convert, 
			@RequestParam(required = false, name = "convert_id") String convertId) {
		return this.cryptocurrencyService.getMarketPairsLatest(
				id, slug, symbol, start, limit, sortDir, sort, aux, matchedId, matchedSymbol, 
				category, feeType, convert, convertId);
	}
	
	/**
	 * <p>Returns the latest OHLCV (Open, High, Low, Close, Volume) market values for one or more cryptocurrencies for the current UTC day. Since the current UTC day is still active these values are updated frequently. You can find the final calculated OHLCV values for the last completed UTC day along with all historic days using /cryptocurrency/ohlcv/historical.</p>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Standard</li>
	 * 	<li>Professional</li>
	 * 	<li>Enterprise</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Every 5 minutes. Additional OHLCV intervals and 1 minute updates will be available in the future.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 OHLCV values returned (rounded up) and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param id One or more comma-separated cryptocurrency CoinMarketCap IDs. Example: 1,2
	 * @param symbol Alternatively pass one or more comma-separated cryptocurrency symbols. Example: "BTC,ETH". At least one "id" or "symbol" is required.
	 * @param convert Optionally calculate market quotes in up to 120 currencies at once by passing a comma-separated list of cryptocurrency or fiat currency symbols. Each additional convert option beyond the first requires an additional call credit. A list of supported fiat options can be found here. Each conversion is returned in its own "quote" object.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * @param skipInvalid Pass true to relax request validation rules. When requesting records on multiple cryptocurrencies an error is returned if any invalid cryptocurrencies are requested or a cryptocurrency does not have matching records in the requested timeframe. If set to true, invalid lookups will be skipped allowing valid cryptocurrencies to still be returned. Default: false
	 * 
	 * @return Latest OHLCV (Open, High, Low, Close, Volume) market values for one or more cryptocurrencies for the current UTC day.
	 */
	@GetMapping(path = "ohlcv/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getOhlcvLatest(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String symbol, 
			@RequestParam(required = false) String convert, 
			@RequestParam(required = false, name = "convert_id") String convertId,
			@RequestParam(required = false, name = "skip_invalid") Boolean skipInvalid) {
		return this.cryptocurrencyService.getOhlcvLatest(id, symbol, convert, convertId, skipInvalid);
	}
	
	/**
	 * <p>Returns historical OHLCV (Open, High, Low, Close, Volume) data along with market cap for any cryptocurrency using time interval parameters. Currently daily and hourly OHLCV periods are supported. Volume is only supported with daily periods at this time.</p>
	 * 
	 * <p><b>Technical Notes</b></p>
	 * <ul>
	 * 	<li>Only the date portion of the timestamp is used for daily OHLCV so it's recommended to send an ISO date format like "2018-09-19" without time for this "time_period".</li>
	 * 	<li>One OHLCV quote will be returned for every "time_period" between your "time_start" (exclusive) and "time_end" (inclusive).</li>
	 * 	<li>If a "time_start" is not supplied, the "time_period" will be calculated in reverse from "time_end" using the "count" parameter which defaults to 10 results.</li>
	 *  <li>If "time_end" is not supplied, it defaults to the current time.</li>
	 *  <li>If you don't need every "time_period" between your dates you may adjust the frequency that "time_period" is sampled using the "interval" parameter. For example with "time_period" set to "daily" you may set "interval" to "2d" to get the daily OHLCV for every other day. You could set "interval" to "monthly" to get the first daily OHLCV for each month, or set it to "yearly" to get the daily OHLCV value against the same date every year.</li>
	 * </ul>
	 * 
	 * <p><b>Implementation Tips</b></p>
	 * <ul>
	 * 	<li>If querying for a specific OHLCV date your "time_start" should specify a timestamp of 1 interval prior as "time_start" is an exclusive time parameter (as opposed to "time_end" which is inclusive to the search). This means that when you pass a "time_start" results will be returned for the next complete "time_period". For example, if you are querying for a daily OHLCV datapoint for 2018-11-30 your "time_start" should be "2018-11-29".</li>
	 * 	<li>If only specifying a "count" parameter to return latest OHLCV periods, your "count" should be 1 number higher than the number of results you expect to receive. "Count" defines the number of "time_period" intervals queried, not the number of results to return, and this includes the currently active time period which is incomplete when working backwards from current time. For example, if you want the last daily OHLCV value available simply pass "count=2" to skip the incomplete active time period.</li>
	 * 	<li>This endpoint supports requesting multiple cryptocurrencies in the same call. Please note the API response will be wrapped in an additional object in this case.</li>
	 * </ul>
	 * 
	 * <p><b>Interval Options</b></p>
	 * <p>There are 2 types of time interval formats that may be used for "time_period" and "interval" parameters. For "time_period" these return aggregate OHLCV data from the beginning to end of each interval period. Apply these time intervals to "interval" to adjust how frequently "time_period" is sampled.</p>
	 * 
	 * <p>The first are calendar year and time constants in UTC time:</p>
	 * <ul>
	 * 	<li><b>"hourly" -</b> Hour intervals in UTC.</li>
	 * 	<li><b>"daily" -</b> Calendar day intervals for each UTC day.</li>
	 * 	<li><b>"weekly" -</b> Calendar week intervals for each calendar week.</li>
	 * 	<li><b>"monthly" -</b> Calendar month intervals for each calendar month.</li>
	 * 	<li><b>"yearly" -</b> Calendar year intervals for each calendar year.</li>
	 * </ul>
	 * 
	 * <p>The second are relative time intervals.</p>
	 * <ul>
	 * 	<li><b>"h":</b> Get the first quote available every "h" hours (3600 second intervals). Supported hour intervals are: "1h", "2h", "3h", "4h", "6h", "12h".</li>
	 * 	<li><b>"d":</b> Time periods that repeat every "d" days (86400 second intervals). Supported day intervals are: "1d", "2d", "3d", "7d", "14d", "15d", "30d", "60d", "90d", "365d".</li>
	 * </ul>
	 * 
	 * <p>Please note that "time_period" currently supports the "daily" and "hourly" options. "interval" supports all interval options.</p>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 *  <li>Startup (1 month)</li>
	 * 	<li>Standard (3 months)</li>
	 * 	<li>Professional (12 months)</li>
	 * 	<li>Enterprise (Up to 6 years)</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Latest Daily OHLCV record is available ~5 to ~10 minutes after each midnight UTC. The latest hourly OHLCV record is available 5 minutes after each UTC hour.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 OHLCV data points returned (rounded up) and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param id One or more comma-separated CoinMarketCap cryptocurrency IDs. Example: "1,1027"
	 * @param slug Alternatively pass a comma-separated list of cryptocurrency slugs. Example: "bitcoin,ethereum"
	 * @param symbol Alternatively pass one or more comma-separated cryptocurrency symbols. Example: "BTC,ETH". At least one "id" or "slug" or "symbol" is required for this request.
	 * @param timePeriod Time period to return OHLCV data for. The default is "daily". See the main endpoint description for details. Default: "daily". Valid values: "daily", "hourly"
	 * @param timeStart Timestamp (Unix or ISO 8601) to start returning OHLCV time periods for. Only the date portion of the timestamp is used for daily OHLCV so it's recommended to send an ISO date format like "2018-09-19" without time.
	 * @param timeEnd Timestamp (Unix or ISO 8601) to stop returning OHLCV time periods for (inclusive). Optional, if not passed we'll default to the current time. Only the date portion of the timestamp is used for daily OHLCV so it's recommended to send an ISO date format like "2018-09-19" without time.
	 * @param count Optionally limit the number of time periods to return results for. The default is 10 items. The current query limit is 10000 items. Default: 10
	 * @param interval Optionally adjust the interval that "time_period" is sampled. See main endpoint description for available options. Default: "daily". Valid values: "hourly", "daily", "weekly", "monthly", "yearly", "1h", "2h", "3h", "4h", "6h", "12h", "1d", "2d", "3d", "7d", "14d", "15d" "30d", "60d", "90d", "365d"
	 * @param convert By default market quotes are returned in USD. Optionally calculate market quotes in up to 3 fiat currencies or cryptocurrencies.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * @param skipInvalid Pass true to relax request validation rules. When requesting records on multiple cryptocurrencies an error is returned if any invalid cryptocurrencies are requested or a cryptocurrency does not have matching records in the requested timeframe. If set to true, invalid lookups will be skipped allowing valid cryptocurrencies to still be returned. Default: false
	 * 
	 * @return Historical OHLCV (Open, High, Low, Close, Volume) data along with market cap for any cryptocurrency using time interval parameters.
	 */
	@GetMapping(path = "ohlcv/historical", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getOhlcvHistorical(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String slug, 
			@RequestParam(required = false) String symbol, 
			@RequestParam(required = false, name = "time_period") String timePeriod, 
			@RequestParam(required = false, name = "time_start") String timeStart,
			@RequestParam(required = false, name = "time_end") String timeEnd,
			@RequestParam(required = false) Long count,
			@RequestParam(required = false) String interval,
			@RequestParam(required = false) String convert, 
			@RequestParam(required = false, name = "convert_id") String convertId,
			@RequestParam(required = false, name = "skip_invalid") Boolean skipInvalid) {
		return this.cryptocurrencyService.getOhlcvHistorical(
				id, slug, symbol, timePeriod, timeStart, timeEnd, count, 
				interval, convert, convertId, skipInvalid);
	}
	
	/**
	 * <p>Returns price performance statistics for one or more cryptocurrencies including launch price ROI and all-time high / all-time low. Stats are returned for an all_time period by default. UTC yesterday and a number of rolling time periods may be requested using the time_period parameter. Utilize the convert parameter to translate values into multiple fiats or cryptocurrencies using historical rates.</p>
	 * 
	 * <p><b>NOTE: You may also use /cryptocurrency/ohlcv/historical for traditional OHLCV data at historical daily and hourly intervals. You may also use /v1/cryptocurrency/ohlcv/latest for OHLCV data for the current UTC day.</b></p>
	 * 
	 * <p><b>This endpoint is available on the following API plans:</b></p>
	 * <ul>
	 * 	<li>Startup</li>
	 * 	<li>Standard</li>
	 * 	<li>Professional</li>
	 * 	<li>Enterprise</li>
	 * </ul>
	 * 
	 * <p><b>Cache / Update frequency:</b> Every 60 seconds.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per 100 cryptocurrencies returned (rounded up) and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param id One or more comma-separated cryptocurrency CoinMarketCap IDs. Example: 1,2
	 * @param slug Alternatively pass a comma-separated list of cryptocurrency slugs. Example: "bitcoin,ethereum"
	 * @param symbol Alternatively pass one or more comma-separated cryptocurrency symbols. Example: "BTC,ETH". At least one "id" or "slug" or "symbol" is required for this request.
	 * @param timePeriod Specify one or more comma-delimited time periods to return stats for. all_time is the default. Pass all_time,yesterday,24h,7d,30d,90d,365d to return all supported time periods. All rolling periods have a rolling close time of the current request time. For example 24h would have a close time of now and an open time of 24 hours before now. Please note: yesterday is a UTC period and currently does not currently support high and low timestamps. Default: "all_time"
	 * @param convert Optionally calculate quotes in up to 120 currencies at once by passing a comma-separated list of cryptocurrency or fiat currency symbols. Each additional convert option beyond the first requires an additional call credit. Each conversion is returned in its own "quote" object.
	 * @param convertId Optionally calculate quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * 
	 * @return Latest Price performance statistics for one or more cryptocurrencies including launch price ROI and all-time high / all-time low.
	 */
	@GetMapping(path = "price-performance-stats/latest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getPricePerformanceStatsLatest(
			@RequestParam(required = false) String id, 
			@RequestParam(required = false) String slug, 
			@RequestParam(required = false) String symbol, 
			@RequestParam(required = false, name = "time_period") String timePeriod,
			@RequestParam(required = false) String convert, 
			@RequestParam(required = false, name = "convert_id") String convertId) {
		return this.cryptocurrencyService.getPricePerformanceStatsLatest(
				id, slug, symbol, timePeriod, convert, convertId);
	}
}

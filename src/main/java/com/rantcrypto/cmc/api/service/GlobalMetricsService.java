/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * <p>Service worker layer for {@link com.rantcrypto.cmc.api.controller.GlobalMetricsController }</p>
 * 
 * @author Phillip Groves
 * @author CoinMarketCap (documentation / data)
 */
@Service
public class GlobalMetricsService extends CoinMarketCapService {

	@Value("${com.rantcrypto.cmc.api.v1.global-metrics.quotes-latest-endpoint:/v1/global-metrics/quotes/latest}") 
	private String GLOBAL_METRICS_QUOTES_LATEST_ENDPOINT;
	
	@Value("${com.rantcrypto.cmc.api.v1.global-metrics.quotes-latest-endpoint.cache-time:-1}") 
	private Long GLOBAL_METRICS_QUOTES_LATEST_ENDPOINT_CACHE_TIME;
	
	@Value("${com.rantcrypto.cmc.api.v1.global-metrics.quotes-historical-endpoint:/v1/global-metrics/quotes/historical}") 
	private String GLOBAL_METRICS_QUOTES_HISTORICAL_ENDPOINT;
	
	@Value("${com.rantcrypto.cmc.api.v1.global-metrics.quotes-historical-endpoint.cache-time:-1}") 
	private Long GLOBAL_METRICS_QUOTES_HISTORICAL_ENDPOINT_CACHE_TIME;

	
	/**
	 * <p>Returns the latest global cryptocurrency market metrics. Use the "convert" option to return market values in multiple fiat and cryptocurrency conversions in the same call.</p>
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
	 * <p><b>Cache / Update frequency:</b> Every 1 minute.</p>
	 * <p><b>Plan credit use:</b> 1 call credit per call and 1 call credit per convert option beyond the first.</p>
	 * 
	 * @param convert Optionally calculate market quotes in up to 120 currencies at once by passing a comma-separated list of cryptocurrency or fiat currency symbols. Each additional convert option beyond the first requires an additional call credit. Each conversion is returned in its own "quote" object.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * 
	 * @return The latest market quote for 1 or more cryptocurrencies.
	 */
	public ResponseEntity<Object> getQuotesLatest(String convert, String convertId) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("convert", convert);
		paramMap.put("convert_id", convertId);
		
		return super.getResponseFromEndpoint(GLOBAL_METRICS_QUOTES_LATEST_ENDPOINT, paramMap);
	}

	/**
	 * <p>Returns an interval of historical global cryptocurrency market metrics based on time and interval parameters.</p>
	 * 
	 * <p><b>Technical Notes</b></p>
	 * <ul>
	 * 	<li>A historic quote for every "interval" period between your "time_start" and "time_end" will be returned.</li>
	 * 	<li>If a "time_start" is not supplied, the "interval" will be applied in reverse from "time_end".</li>
	 * 	<li>If "time_end" is not supplied, it defaults to the current time.</li>
	 * 	<li>At each "interval" period, the historic quote that is closest in time to the requested time will be returned.</li>
	 * 	<li>If no historic quotes are available in a given "interval" period up until the next interval period, it will be skipped.</li>
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
	 * <p><b>Plan credit use:</b> 1 call credit per 100 historical data points returned (rounded up).</p>
	 * 
	 * @param timeStart Timestamp (Unix or ISO 8601) to start returning quotes for. Optional, if not passed, we'll return quotes calculated in reverse from "time_end".
	 * @param timeEnd Timestamp (Unix or ISO 8601) to stop returning quotes for (inclusive). Optional, if not passed, we'll default to the current time. If no "time_start" is passed, we return quotes in reverse order starting from this time.
	 * @param count The number of interval periods to return results for. Optional, required if both "time_start" and "time_end" aren't supplied. The default is 10 items. The current query limit is 10000. Default: 10
	 * @param interval Interval of time to return data points for. See details in endpoint description. Default: "1d". Valid values: "yearly", "monthly" "weekly", "daily", "hourly""5m", "10m", "15m", "30m""45m", "1h", "2h", "3h", "4h", "6h", "12h", "24h", "1d", "2d" "3d", "7d", "14d", "15d", "30d", "60d", "90d", "365d"
	 * @param convert By default market quotes are returned in USD. Optionally calculate market quotes in up to 3 other fiat currencies or cryptocurrencies.
	 * @param convertId Optionally calculate market quotes by CoinMarketCap ID instead of symbol. This option is identical to convert outside of ID format. Ex: convert_id=1,2781 would replace convert=BTC,USD in your query. This parameter cannot be used when convert is used.
	 * @param aux Optionally specify a comma-separated list of supplemental data fields to return. Pass btc_dominance,active_cryptocurrencies,active_exchanges,active_market_pairs,total_volume_24h,total_volume_24h_reported,altcoin_market_cap,altcoin_volume_24h,altcoin_volume_24h_reported,search_interval to include all auxiliary fields. Default: "btc_dominance,active_cryptocurrencies,active_exchanges,active_market_pairs,total_volume_24h,total_volume_24h_reported,altcoin_market_cap,altcoin_volume_24h,altcoin_volume_24h_reported"
	 * 
	 * @return An interval of historical global cryptocurrency market metrics based on time and interval parameters.
	 */
	public ResponseEntity<Object> getQuotesHistorical(String timeStart, String timeEnd, Long count, String interval, String convert,
			String convertId, String aux) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("time_start", timeStart);
		paramMap.put("time_end", timeEnd);
		paramMap.put("count", (count != null ? Long.toString(count) : null));
		paramMap.put("interval", interval);
		paramMap.put("convert", convert);
		paramMap.put("convert_id", convertId);
		paramMap.put("aux", aux);
		
		return super.getResponseFromEndpoint(GLOBAL_METRICS_QUOTES_HISTORICAL_ENDPOINT, paramMap);
	}
}

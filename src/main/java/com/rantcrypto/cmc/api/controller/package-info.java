/**
 * <p>A collection of REST endpoints which correlate to CoinMarketCap endpoints.</p>
 * 
 * <p><em>The CoinMarketCap API is divided into 8 top-level categories:</em></p>
 * <ul>
 * 	<li><b>/cryptocurrency/* -</b> Endpoints that return data around cryptocurrencies such as ordered cryptocurrency lists or price and volume data.</li>
 * 	<li><b>/exchange/* -</b> Endpoints that return data around cryptocurrency exchanges such as ordered exchange lists and market pair data.</li>
 * 	<li><b>/global-metrics/* -</b> Endpoints that return aggregate market data such as global market cap and BTC dominance.</li>
 * 	<li><b>/tools/* -</b> Useful utilities such as cryptocurrency and fiat price conversions.</li>
 * 	<li><b>/blockchain/* -</b> Endpoints that return block explorer related data for blockchains.</li>
 * 	<li><b>/fiat/* -</b> Endpoints that return data around fiats currencies including mapping to CMC IDs.</li>
 * 	<li><b>/partners/* -</b> Endpoints for convenient access to 3rd party crypto data.</li>
 * 	<li><b>/key/* -</b> API key administration endpoints to review and manage your usage.</li>
 * </ul>
 * 
 * <p><em>Endpoint paths follow a pattern matching the type of data provided:</em></p>
 * <ul>
 * 	<li><b>/latest -</b> Latest Market Data; Latest market ticker quotes and averages for cryptocurrencies and exchanges.</li>
 * 	<li><b>/historical -</b> Historical Market Data; Intervals of historic market data like OHLCV data or data for use in charting libraries.</li>
 * 	<li><b>/info -</b> Metadata; Cryptocurrency and exchange metadata like block explorer URLs and logos.</li>
 * 	<li><b>/map -</b> ID Maps; Utility endpoints to get a map of resources to CoinMarketCap IDs.</li>
 * </ul>
 * 
 * <p><em>Cryptocurrency and exchange endpoints provide 2 different ways of accessing data depending on purpose</em></p>
 * <ul>
 * 	<li><b>Listing endpoints:</b> Flexible paginated /listings/ endpoints allow you to sort and filter lists of data like cryptocurrencies by market cap or exchanges by volume.</li>
 * 	<li><b>Item endpoints:</b> Convenient ID-based resource endpoints like /quotes/ and /market-pairs/ allow you to bundle several IDs; for example, this allows you to get latest market quotes for a specific set of cryptocurrencies in one call.</li>
 * </ul>
 * 
 * 
 * @since 1.0
 * @version 1.0
 * @author Phillip Groves
 * @author CoinMarketCap (documentation / data)
 */
package com.rantcrypto.cmc.api.controller;
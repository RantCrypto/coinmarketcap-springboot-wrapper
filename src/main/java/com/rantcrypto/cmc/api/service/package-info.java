/**
 * A collection of service workers which are responsible for sending requests and returning data 
 * from specific CoinMarketCap API endpoints. Each response from the CoinMarketCap API is returned 
 * in JSON format. For every endpoint defined in the {@link com.rantcrypto.cmc.api.controller } package, 
 * there is an equivalent service worker method.
 * 
 * @since 1.0
 * @version 1.0
 * @author Phillip Groves
 * @author CoinMarketCap (documentation / data)
 */
package com.rantcrypto.cmc.api.service;
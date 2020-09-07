# CoinMarketCap Spring Boot Wrapper
A simple wrapper for the CoinMarketCap API built as a reusable & configurable Spring Boot microservice. The goal is to provide a simple configurable service for procuring cryptocurrency and exchange data from the CoinMarketCap API. As a way to protect your unique API key, CoinMarketCap disallows developers from calling their API from a client using Javascript (CORS restriction). This service, written in Java, can act as a mediator between your client and the CoinMarketCap API. For larger projects, this service can be called from other server-side services to support a full microservice architecture and/or implement caching.

Each REST endpoint of the Spring Boot application correlates to an endpoint of the CoinMarketCap API. For example, a call to https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest and http://localhost:9898/v1/cryptocurrency/listings/latest will return the same data.

For more information on REST endpoints and CoinMarketCap data, check out the Javadoc within the `/doc/` folder or https://coinmarketcap.com/api/documentation/v1/

**NOTE:** By default, this service runs on port `9898`. This can be changed in its application.properties file.

**_RantCrypto and its developers are not affiliated, sponsored, nor otherwise endorsed by CoinMarketCap. Special care has been taken to acheive compatibility with CoinMarketCap's Commercial User Agreement (https://pro.coinmarketcap.com/user-agreement-commercial). When you are using CoinMarketCap Spring Boot Wrapper, you must adhere to this agreement._**

## Quick Start
1. If you have not already, sign up for a developer account to obtain an API key at https://pro.coinmarketcap.com/signup
2. Download or clone the code from this repository. If downloaded as a .zip file, extract the code.
3. Navigate to application.properties and assign your API key to `com.rantcrypto.cmc.api.v1.api-key`. Save the file.
4. In a command prompt or terminal, navigate into the project folder. Run the `./mvnw spring-boot:run` command to start the application.
5. Test your configuration by navigating to http://localhost:9898/v1/key/info in your web browser.

## Notes
There are a few important things to note before using this service:
- For security and cost-saving in commercial projects, it is *highly* recommended to implement a caching mechanism.
- Use CoinMarketCap id's instead of symbols or slugs where possible. Some cryptocurrencies share the same symbol.
- By default, this service will reach out to the CoinMarketCap sandbox at `https://sandbox-api.coinmarketcap.com`. To connect to the real-time pro API, set `com.rantcrypto.cmc.api.v1.base-url=https://pro-api.coinmarketcap.com` in application.properties

## Roadmap
The status of the project roadmap can be found below:
- [x] Host Spring Boot REST endpoints which correlate to every CoinMarketCap API endpoint
- [x] Develop a service layer to parse parameters & get a response from the CoinMarketCap API; Each worker is called from a Spring Boot REST endpoint
- [x] Add documentation which correlates to the official documentation at https://coinmarketcap.com/api/documentation/v1/ and generate Javadoc
- [x] Handle all exceptions from the CoinMarketCap API and forward them back to the client with the same status code and response body
- [x] Configure the service easily through application.properties file. Change the base url (sandbox or pro) and add your own API key
- [ ] JUnit unit testing for all endpoints
- [ ] A simple caching mechanism which is configurable in application.properties
- [ ] Add support for simple JSON transformations in application.properties
- [ ] Add profiles to automatically use CoinMarketCap's sandbox API in dev/test environments, and their pro API in production environment
- [ ] Add ability to obtain API key from a secure S3 Bucket for higher security
- [ ] Add support for additional features, such as real-time pricing and `/v1/exchange/listings/historical`, when CoinMarketCap makes them available through their API

## Configure with application.properties

### Required
These key-values pairs should be set in application.properties for this service to work correctly
- `com.rantcrypto.cmc.api.v1.api-key=<your-api-key>`
- `com.rantcrypto.cmc.api.v1.base-url=https://pro-api.coinmarketcap.com` if running is in a production environment

### Optional
These key-value pairs can optionally be set in application.properties. They have default values, which are displayed below:
- `com.rantcrypto.cmc.api.v1.header=X-CMC_PRO_API_KEY`

- `com.rantcrypto.cmc.api.v1.cryptocurrency.map-endpoint=/v1/cryptocurrency/map`
- `com.rantcrypto.cmc.api.v1.cryptocurrency.info-endpoint=/v1/cryptocurrency/info`
- `com.rantcrypto.cmc.api.v1.cryptocurrency.listings-latest-endpoint=/v1/cryptocurrency/listings/latest`
- `com.rantcrypto.cmc.api.v1.cryptocurrency.listings-historical-endpoint=/v1/cryptocurrency/listings/historical`
- `com.rantcrypto.cmc.api.v1.cryptocurrency.quotes-latest-endpoint=/v1/cryptocurrency/quotes/latest`
- `com.rantcrypto.cmc.api.v1.cryptocurrency.quotes-historical-endpoint=/v1/cryptocurrency/quotes/historical`
- `com.rantcrypto.cmc.api.v1.cryptocurrency.market-pairs-latest-endpoint=/v1/cryptocurrency/market-pairs/latest`
- `com.rantcrypto.cmc.api.v1.cryptocurrency.ohlcv-latest-endpoint=/v1/cryptocurrency/ohlcv/latest`
- `com.rantcrypto.cmc.api.v1.cryptocurrency.ohlcv-historical-endpoint=/v1/cryptocurrency/ohlcv/historical`
- `com.rantcrypto.cmc.api.v1.cryptocurrency.price-performance-stats-latest-endpoint=/v1/cryptocurrency/price-performance-stats/latest`

- `com.rantcrypto.cmc.api.v1.exchange.map-endpoint=/v1/exchange/map`
- `com.rantcrypto.cmc.api.v1.exchange.info-endpoint=/v1/exchange/info`
- `com.rantcrypto.cmc.api.v1.exchange.listings-latest-endpoint=/v1/exchange/listings/latest`
- `com.rantcrypto.cmc.api.v1.exchange.listings-historical-endpoint=/v1/exchange/listings/historical`
- `com.rantcrypto.cmc.api.v1.exchange.quotes-latest-endpoint=/v1/exchange/quotes/latest`
- `com.rantcrypto.cmc.api.v1.exchange.quotes-historical-endpoint=/v1/exchange/quotes/historical`
- `com.rantcrypto.cmc.api.v1.exchange.market-pairs-latest-endpoint=/v1/exchange/market-pairs/latest`

- `com.rantcrypto.cmc.api.v1.global-metrics.quotes-latest-endpoint=/v1/global-metrics/quotes/latest`
- `com.rantcrypto.cmc.api.v1.global-metrics.quotes-historical-endpoint=/v1/global-metrics/quotes/historical`

- `com.rantcrypto.cmc.api.v1.tools.price-conversion-endpoint=/v1/tools/price-conversion`

- `com.rantcrypto.cmc.api.v1.blockchain.statistics-latest-endpoint=/v1/blockchain/statistics/latest`

- `com.rantcrypto.cmc.api.v1.fiat.map-endpoint=/v1/fiat/map`

- `com.rantcrypto.cmc.api.v1.partners.flipside-crypto-fcas-listings-latest-endpoint=/v1/partners/flipside-crypto/fcas/listings/latest`
- `com.rantcrypto.cmc.api.v1.partners.flipside-crypto-fcas-quotes-latest-endpoint=/v1/partners/flipside-crypto/fcas/quotes/latest`

- `com.rantcrypto.cmc.api.v1.key.info-endpoint=/v1/key/info`

## Current testing status

Unit Testing Coverage: `81.9%`

- [x] Basic CoinMarketCap plan endpoints
- [x] Hobbyist CoinMarketCap plan endpoints
- [ ] Startup CoinMarketCap plan endpoints
- [ ] Standard CoinMarketCap plan endpoints
- [ ] Enterprise CoinMarketCap plan endpoints

## Reporting an Issue
If you encounter an issue and you believe this service to be the cause, not the CoinMarketCap API nor your environment, please report an issue in this repository.

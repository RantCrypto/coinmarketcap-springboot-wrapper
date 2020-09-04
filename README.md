# CoinMarketCap Spring Boot Wrapper
A simple wrapper for the CoinMarketCap API built as a reusable & configurable Spring Boot microservice. The goal is to provide a simple configurable service for procuring cryptocurrency and exchange data from the CoinMarketCap API. As a way to protect your unique API key, CoinMarketCap disallows developers from calling their API from a client using Javascipt (CORS restriction). This service, written in Java, can act as a mediator between your client and the CoinMarketCap API. For larger projects, this service can be called from other server-side services to support a full microservice architecture and/or implement caching.

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
- For security and cost-saving in commercial projects, it is *highly* recommended to implement a caching mechanism.
- Use CoinMarketCap id's instead of symbols or slugs where possible. Some cryptocurrencies share the same symbol.
- ~~By default, this service will reach out to the CoinMarketCap sandbox at `https://sandbox-api.coinmarketcap.com`. To connect to the real-time pro API, set `com.rantcrypto.cmc.api.v1.base-url=https://pro-api.coinmarketcap.com` in application.properties~~
- CoinMarketCap's sandbox environment is temporarily unavailable. `com.rantcrypto.cmc.api.v1.base-url=https://pro-api.coinmarketcap.com` is temporarily set in application.properties by default.

## Reporting an Issue
If you encounter an issue while using this service which you believe is caused by this Spring Boot application, and not the CoinMarketCap API itself, please file an issue in this repository.

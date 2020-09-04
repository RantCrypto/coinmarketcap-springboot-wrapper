/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple POJO matching the content of a CoinMarketCap error object. This object only contains an object 
 * called status which holds error code, message, etc.
 * 
 * @author Phillip Groves
 */
public class ApiException {

	private CoinMarketCapStatus status;

	public ApiException(Long statusCode, String error) {
		TimeZone timezone = TimeZone.getTimeZone("UTC");
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		formatter.setTimeZone(timezone);
		Date currentDateTime = new Date();
		
		this.status = new CoinMarketCapStatus();
		status.setTimestamp(formatter.format(currentDateTime));
		status.setError_code(statusCode);
		status.setError_message(error);
		status.setElapsed(0L);
		status.setCredit_count(0L);
	}

	public CoinMarketCapStatus getStatus() {
		return status;
	}

	public void setStatus(CoinMarketCapStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CoinMarketCapException [status=" + status + "]";
	}
}

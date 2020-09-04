package com.rantcrypto.cmc.api;

/**
 * A simple POJO matching the content of a CoinMarketCap status object. This object is included in 
 * the response body of any CoinMarketCap API request.
 * 
 * @author Phillip Groves
 */
public class ApiExceptionStatus {

	private String timestamp;
	private Long error_code;
	private String error_message;
	private Long elapsed;
	private Long credit_count;
	
	
	public ApiExceptionStatus(String timestamp, Long error_code, String error_message, Long elapsed,
			Long credit_count) {
		super();
		this.timestamp = timestamp;
		this.error_code = error_code;
		this.error_message = error_message;
		this.elapsed = elapsed;
		this.credit_count = credit_count;
	}
	
	public ApiExceptionStatus() {
		this.timestamp = "unknown";
		this.error_code = 0L;
		this.error_message = "unknown error";
		this.elapsed = 0L;
		this.credit_count = 0L;
	}

	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public Long getError_code() {
		return error_code;
	}
	
	public void setError_code(Long error_code) {
		this.error_code = error_code;
	}
	
	public String getError_message() {
		return error_message;
	}
	
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	
	public Long getElapsed() {
		return elapsed;
	}
	
	public void setElapsed(Long elapsed) {
		this.elapsed = elapsed;
	}
	
	public Long getCredit_count() {
		return credit_count;
	}
	
	public void setCredit_count(Long credit_count) {
		this.credit_count = credit_count;
	}
}

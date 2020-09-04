/* https://pro.coinmarketcap.com/user-agreement-commercial */

package com.rantcrypto.cmc.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * <p>A handler for exceptions thrown by Spring Boot while accepting REST requests. If Spring Boot 
 * throws an exception before sending a request to the CoinMarketCap API, the methods in this class will 
 * handle the errors and format responses to match those of the CoinMarketCap API. This enables full compatibility 
 * for developers whom already handle errors from the CoinMarketCap API. In some cases, these exceptions  
 * can prevent invalid data from being sent to the CoinMarketCap API.</p>
 * 
 * @author Phillip Groves
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String ILLEGAL_PARAMETER_TYPE_MESSAGE = "\"" + "%param_name%" + "\"" + " must be a " + "%param_type%";
	
	/**
	 * <p>The server could not process the request, likely due to an invalid argument.</p>
	 * 
	 * @param exception The current exception injected by Spring
	 * @param request The current web request injected by Spring
	 * 
	 * @return Error 400 Bad Request
	 */
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
	  MethodArgumentTypeMismatchException exception, WebRequest request) {
	    
		String requiredType = "";
	    switch (exception.getRequiredType().getName()) {
	    case "java.lang.Long":
	    case "java.lang.Double":
	    	requiredType = "number";
	    	break;
	    case "java.lang.Boolean":
	    	requiredType = "boolean";
	    	break;
	    }
	    
	    String errorMessage = ILLEGAL_PARAMETER_TYPE_MESSAGE
	    		.replace("%param_name%", exception.getName())
	    		.replace("%param_type%", requiredType);
	 
	    ApiException coinMarketCapException = new ApiException(
	    		(long) HttpStatus.BAD_REQUEST.value(), errorMessage);
	    
	    return new ResponseEntity<Object>(
	    		coinMarketCapException, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}

package com.prasad.ticketbooking.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.prasad.ticketbooking.model.ErrorDetails;
/**
 * Global Exception handling.
 * @author prasa
 *
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler  {

	private static final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);


	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDetails> handleAllExceptions(NullPointerException ex) {
		logger.error("---Start handleAllExceptions global Exception ---- ");
		String bodyOfResponse = "Internal Server.";
		ErrorDetails error=new ErrorDetails(100,bodyOfResponse, ex.getMessage());
		logger.error("---end handleAllExceptions global exception ---- ");
		return new ResponseEntity<ErrorDetails>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	


	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ErrorDetails> handleAllExceptions(Throwable ex) {
		logger.error("---Start handleAllExceptions global Exception ---- ");
		String bodyOfResponse = "Internal Server.";
		ErrorDetails error=new ErrorDetails(102,bodyOfResponse, ex.getMessage());
		logger.error("---end handleAllExceptions global exception ---- ");
		return new ResponseEntity<ErrorDetails>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	//TODO add all global Exception handlers.
}

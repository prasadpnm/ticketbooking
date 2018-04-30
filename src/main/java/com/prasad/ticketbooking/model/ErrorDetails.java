package com.prasad.ticketbooking.model;

import java.io.Serializable;

/**
 * Error Pojo
 * @author prasa
 *
 */

public class ErrorDetails implements Serializable {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int errorCode;
	  private String message;
	  private String details;

	  public ErrorDetails( int erroCode, String message, String details) {
	    super();
	    this.errorCode=erroCode;
	    this.message = message;
	    this.details = details;
	  }

	

	public int getErrorCode() {
		return errorCode;
	}



	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}

package com.revature.exception;

public class ReimTicketImageNotFoundException extends Exception {

	public ReimTicketImageNotFoundException() {
		super();
	}

	public ReimTicketImageNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReimTicketImageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimTicketImageNotFoundException(String message) {
		super(message);
	}

	public ReimTicketImageNotFoundException(Throwable cause) {
		super(cause);
	}

}
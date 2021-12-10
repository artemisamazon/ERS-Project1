package com.revature.exception;

public class ReimTicketNotFoundException extends Exception {

	public ReimTicketNotFoundException() {
		super();
	}

	public ReimTicketNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ReimTicketNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReimTicketNotFoundException(String message) {
		super(message);
	}

	public ReimTicketNotFoundException(Throwable cause) {
		super(cause);
	}

}
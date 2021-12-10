package com.revature.exception;

public class PreviousTicketCompletedException extends Exception {

	public PreviousTicketCompletedException() {
		super();
	}

	public PreviousTicketCompletedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PreviousTicketCompletedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PreviousTicketCompletedException(String message) {
		super(message);
	}

	public PreviousTicketCompletedException(Throwable cause) {
		super(cause);
	}

}
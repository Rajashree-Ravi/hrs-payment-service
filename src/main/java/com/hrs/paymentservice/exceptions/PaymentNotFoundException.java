package com.hrs.paymentservice.exceptions;

public class PaymentNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaymentNotFoundException() {
		super();
	}

	public PaymentNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}

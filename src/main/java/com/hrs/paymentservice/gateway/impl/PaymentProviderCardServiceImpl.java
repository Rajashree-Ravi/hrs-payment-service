package com.hrs.paymentservice.gateway.impl;

import org.springframework.stereotype.Component;

import com.hrs.paymentservice.gateway.PaymentProviderService;
import com.hrs.paymentservice.gateway.request.PaymentProviderCardRequest;
import com.hrs.paymentservice.gateway.response.PaymentProviderCardResponse;
import com.hrs.paymentservice.models.PaymentStatus;

@Component
public class PaymentProviderCardServiceImpl
		implements PaymentProviderService<PaymentProviderCardRequest, PaymentProviderCardResponse> {

	@Override
	public PaymentProviderCardResponse pay(PaymentProviderCardRequest request) {

		PaymentStatus status = PaymentStatus.randomPaymentStatus();

		switch (status) {
		case SUCCESS:
			return new PaymentProviderCardResponse(status, request.getAmount(), request.getCardHolderName(),
					request.getCardNumber(), "Customer copy generated", Integer.valueOf("5"));
		case FAILED:
			return new PaymentProviderCardResponse(status, request.getAmount(), request.getCardHolderName(),
					request.getCardNumber());
		case PENDING:
			return new PaymentProviderCardResponse(status, request.getAmount(), request.getCardHolderName(),
					request.getCardNumber());
		default:
			throw new UnsupportedOperationException("Payment Status: " + status + " not supported");
		}

	}

	@Override
	public PaymentProviderCardResponse refund(PaymentProviderCardRequest request) {
		return new PaymentProviderCardResponse(PaymentStatus.randomPaymentStatus(), request.getAmount(),
				request.getCardHolderName(), request.getCardNumber());

	}
}

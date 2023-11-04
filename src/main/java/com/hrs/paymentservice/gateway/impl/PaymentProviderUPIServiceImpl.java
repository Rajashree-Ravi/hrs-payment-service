package com.hrs.paymentservice.gateway.impl;

import org.springframework.stereotype.Component;

import com.hrs.paymentservice.gateway.PaymentProviderService;
import com.hrs.paymentservice.gateway.request.PaymentProviderUPIRequest;
import com.hrs.paymentservice.gateway.response.PaymentProviderUPIResponse;
import com.hrs.paymentservice.models.PaymentStatus;

@Component
public class PaymentProviderUPIServiceImpl
		implements PaymentProviderService<PaymentProviderUPIRequest, PaymentProviderUPIResponse> {

	@Override
	public PaymentProviderUPIResponse pay(PaymentProviderUPIRequest request) {

		PaymentStatus status = PaymentStatus.randomPaymentStatus();

		switch (status) {
		case SUCCESS:
			return new PaymentProviderUPIResponse(status, request.getAmount(), request.getVirtualPaymentAddress(),
					request.getUserComments(), Integer.valueOf("25"));
		case FAILED:
			return new PaymentProviderUPIResponse(status, request.getAmount(), request.getVirtualPaymentAddress());
		case PENDING:
			return new PaymentProviderUPIResponse(status, request.getAmount(), request.getVirtualPaymentAddress());
		default:
			throw new UnsupportedOperationException("Payment Status: " + status + " not supported");
		}

	}

	@Override
	public PaymentProviderUPIResponse refund(PaymentProviderUPIRequest request) {
		return new PaymentProviderUPIResponse(PaymentStatus.randomPaymentStatus(), request.getAmount(),
				request.getVirtualPaymentAddress());
	}
}

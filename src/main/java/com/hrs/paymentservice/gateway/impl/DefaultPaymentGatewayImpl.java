package com.hrs.paymentservice.gateway.impl;

import org.springframework.stereotype.Component;

import com.hrs.paymentservice.gateway.PaymentGateway;
import com.hrs.paymentservice.gateway.PaymentProvider;
import com.hrs.paymentservice.gateway.PaymentProviderService;
import com.hrs.paymentservice.gateway.request.PaymentRequest;
import com.hrs.paymentservice.gateway.response.PaymentResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DefaultPaymentGatewayImpl implements PaymentGateway<PaymentRequest, PaymentResponse> {

	private final PaymentProvider<PaymentProviderService<PaymentRequest, PaymentResponse>> paymentProvider;

	@Override
	public PaymentResponse makePayment(PaymentRequest paymentRequest) {
		return paymentProvider.getProvider(paymentRequest.getPaymentProviderType()).pay(paymentRequest);
	}

	@Override
	public PaymentResponse makeRefund(PaymentRequest paymentRequest) {
		return paymentProvider.getProvider(paymentRequest.getPaymentProviderType()).pay(paymentRequest);
	}

}

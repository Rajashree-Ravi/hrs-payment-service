package com.hrs.paymentservice.gateway.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.hrs.paymentservice.gateway.PaymentProvider;
import com.hrs.paymentservice.gateway.PaymentProviderService;
import com.hrs.paymentservice.gateway.PaymentProviderType;
import com.hrs.paymentservice.gateway.request.PaymentRequest;
import com.hrs.paymentservice.gateway.response.PaymentResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DefaultPaymentProviderImpl
		implements PaymentProvider<PaymentProviderService<PaymentRequest, PaymentResponse>> {

	private final ApplicationContext applicationContext;

	@Override
	@SuppressWarnings("rawtypes")
	public PaymentProviderService getProvider(PaymentProviderType type) {

		switch (type) {
		case CARD:
			return applicationContext.getBean(PaymentProviderCardServiceImpl.class);
		case UPI:
			return applicationContext.getBean(PaymentProviderUPIServiceImpl.class);
		default:
			throw new UnsupportedOperationException("Payment Provider type: " + type + " not supported");
		}

	}

}

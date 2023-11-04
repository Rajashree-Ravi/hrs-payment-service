package com.hrs.paymentservice.gateway;

public interface PaymentProvider<T> {
	T getProvider(PaymentProviderType type);
}

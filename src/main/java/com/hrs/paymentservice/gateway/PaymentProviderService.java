package com.hrs.paymentservice.gateway;

import com.hrs.paymentservice.gateway.request.PaymentRequest;
import com.hrs.paymentservice.gateway.response.PaymentResponse;

public interface PaymentProviderService<T extends PaymentRequest, R extends PaymentResponse> {

	R pay(T request);

	R refund(T request);
}

package com.hrs.paymentservice.gateway;

import com.hrs.paymentservice.gateway.request.PaymentRequest;
import com.hrs.paymentservice.gateway.response.PaymentResponse;

public interface PaymentGateway<K extends PaymentRequest, T extends PaymentResponse> {

	T makePayment(K paymentRequest);

	T makeRefund(K refundRequest);

}

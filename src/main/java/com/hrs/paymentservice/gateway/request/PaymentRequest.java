package com.hrs.paymentservice.gateway.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.hrs.paymentservice.gateway.PaymentProviderType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PaymentRequest {

	@NotNull
	private BigDecimal amount;

	@NotNull
	private PaymentProviderType paymentProviderType;

	public PaymentRequest(@NotNull PaymentProviderType paymentProviderType, @NotNull BigDecimal amount) {
		super();
		this.paymentProviderType = paymentProviderType;
		this.amount = amount;
	}

}
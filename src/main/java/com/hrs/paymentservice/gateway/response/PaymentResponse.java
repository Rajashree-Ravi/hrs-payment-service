package com.hrs.paymentservice.gateway.response;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.hrs.paymentservice.models.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PaymentResponse {

	@NotNull
	private PaymentStatus status;

	@NotNull
	private BigDecimal amount;

	public PaymentResponse(@NotNull PaymentStatus status, @NotNull BigDecimal amount) {
		super();
		this.status = status;
		this.amount = amount;
	}

}

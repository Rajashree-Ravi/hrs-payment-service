package com.hrs.paymentservice.gateway.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hrs.paymentservice.gateway.PaymentProviderType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentProviderCardRequest extends PaymentRequest {

	@NotBlank
	private String cardNumber;

	@NotBlank
	private String cardHolderName;

	@NotBlank
	private String cvv;

	public PaymentProviderCardRequest(@NotNull PaymentProviderType paymentProviderType, @NotNull BigDecimal amount,
			@NotBlank String cardNumber, @NotBlank String cardHolderName, @NotBlank String cvv) {
		super(paymentProviderType, amount);
		this.cardNumber = cardNumber;
		this.cardHolderName = cardHolderName;
		this.cvv = cvv;
	}

	@Override
	public PaymentProviderType getPaymentProviderType() {
		return PaymentProviderType.CARD;
	}

}

package com.hrs.paymentservice.gateway.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hrs.paymentservice.gateway.PaymentProviderType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentProviderUPIRequest extends PaymentRequest {

	@NotBlank
	private String virtualPaymentAddress;

	private String userComments;

	public PaymentProviderUPIRequest(@NotNull PaymentProviderType paymentProviderType, @NotNull BigDecimal amount,
			@NotBlank String virtualPaymentAddress) {
		super(paymentProviderType, amount);
		this.virtualPaymentAddress = virtualPaymentAddress;
	}

	public PaymentProviderUPIRequest(@NotNull PaymentProviderType paymentProviderType, @NotNull BigDecimal amount,
			@NotBlank String virtualPaymentAddress, String userComments) {
		super(paymentProviderType, amount);
		this.virtualPaymentAddress = virtualPaymentAddress;
		this.userComments = userComments;
	}

	@Override
	public PaymentProviderType getPaymentProviderType() {
		return PaymentProviderType.UPI;
	}

}

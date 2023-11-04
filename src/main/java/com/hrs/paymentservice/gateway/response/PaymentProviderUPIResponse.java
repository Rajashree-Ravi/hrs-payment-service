package com.hrs.paymentservice.gateway.response;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import com.hrs.paymentservice.models.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentProviderUPIResponse extends PaymentResponse {

	@NotBlank
	private String virtualPaymentAddress;

	private String userComments;

	private Integer cashbackPoints;

	public PaymentProviderUPIResponse(PaymentStatus status, BigDecimal amount, String virtualPaymentAddress) {
		super(status, amount);
		this.virtualPaymentAddress = virtualPaymentAddress;
	}

	public PaymentProviderUPIResponse(PaymentStatus status, BigDecimal amount, String virtualPaymentAddress,
			String userComments, Integer cashbackPoints) {
		super(status, amount);
		this.virtualPaymentAddress = virtualPaymentAddress;
		this.userComments = userComments;
		this.cashbackPoints = cashbackPoints;
	}

}

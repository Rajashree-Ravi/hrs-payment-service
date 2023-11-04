package com.hrs.paymentservice.gateway.response;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import com.hrs.paymentservice.models.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentProviderCardResponse extends PaymentResponse {

	@NotBlank
	private String cardNumber;

	@NotBlank
	private String cardHolderName;

	private String customerCopy;

	private Integer rewardPoints;

	public PaymentProviderCardResponse(PaymentStatus status, BigDecimal amount, String cardHolderName,
			String cardNumber) {
		super(status, amount);
		this.cardHolderName = cardHolderName;
		this.cardNumber = cardNumber;
	}

	public PaymentProviderCardResponse(PaymentStatus status, BigDecimal amount, String cardHolderName,
			String cardNumber, String customerCopy, Integer rewardPoints) {
		super(status, amount);
		this.cardHolderName = cardHolderName;
		this.cardNumber = cardNumber;
		this.customerCopy = customerCopy;
		this.rewardPoints = rewardPoints;
	}

}

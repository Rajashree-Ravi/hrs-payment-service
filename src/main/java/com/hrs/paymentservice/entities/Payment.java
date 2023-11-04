package com.hrs.paymentservice.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hrs.paymentservice.gateway.PaymentProviderType;
import com.hrs.paymentservice.models.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Invalid customerId: CustomerId may not be null.")
	private Long customerId;

	@NotNull(message = "Invalid amount: Payment amount may not be null.")
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Invalid provider: Payment provider may not be null.")
	private PaymentProviderType provider;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Invalid status: Payment status may not be null.")
	private PaymentStatus status;

	public Payment updateWith(Payment payment) {
		return new Payment(this.id, payment.customerId, payment.amount, payment.provider, payment.status);
	}

}

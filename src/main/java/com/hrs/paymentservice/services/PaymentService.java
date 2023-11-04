package com.hrs.paymentservice.services;

import java.util.List;

import javax.validation.Valid;

import com.hrs.paymentservice.models.PaymentDto;

public interface PaymentService {

	PaymentDto processTransaction(@Valid PaymentDto paymentDto);

	PaymentDto getPaymentById(@Valid long id);

	List<PaymentDto> getPaymentsByCustomerId(@Valid long customerId);

}

package com.hrs.paymentservice.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hrs.paymentservice.entities.Payment;
import com.hrs.paymentservice.exceptions.PaymentNotFoundException;
import com.hrs.paymentservice.gateway.PaymentGateway;
import com.hrs.paymentservice.gateway.request.PaymentProviderCardRequest;
import com.hrs.paymentservice.gateway.request.PaymentProviderUPIRequest;
import com.hrs.paymentservice.gateway.request.PaymentRequest;
import com.hrs.paymentservice.gateway.response.PaymentResponse;
import com.hrs.paymentservice.models.PaymentDto;
import com.hrs.paymentservice.repositories.PaymentRepository;
import com.hrs.paymentservice.services.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentGateway<PaymentRequest, PaymentResponse> paymentGateway;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public PaymentDto processTransaction(@Valid PaymentDto paymentDto) {

		switch (paymentDto.getType()) {
		case PAY: {
			// Call the gateway for payment
			PaymentResponse response = paymentGateway.makePayment(generatePaymentRequest(paymentDto));
			paymentDto.setStatus(response.getStatus());
		}
		case REFUND: {
			// Call the gateway for refund
			PaymentResponse response = paymentGateway.makeRefund(generatePaymentRequest(paymentDto));
			paymentDto.setStatus(response.getStatus());
		}
		default:
			break;
		}

		paymentRepository.save(mapper.map(paymentDto, Payment.class));
		return paymentDto;
	}

	@Override
	public PaymentDto getPaymentById(@Valid long id) {
		Optional<Payment> payment = paymentRepository.findById(id);
		if (payment.isPresent())
			return mapper.map(payment.get(), PaymentDto.class);
		else
			throw new PaymentNotFoundException("Payment with id: " + id + " not found");
	}

	@Override
	public List<PaymentDto> getPaymentsByCustomerId(@Valid long customerId) {
		List<PaymentDto> payments = new ArrayList<>();
		paymentRepository.findByCustomerId(customerId).forEach(payment -> {
			payments.add(mapper.map(payment, PaymentDto.class));
		});
		return payments;
	}

	private PaymentRequest generatePaymentRequest(PaymentDto paymentDto) {

		switch (paymentDto.getProvider()) {
		case CARD:
			return new PaymentProviderCardRequest(paymentDto.getProvider(), paymentDto.getAmount(),
					paymentDto.getCardNumber(), paymentDto.getCardHolderName(), paymentDto.getCvv());
		case UPI:
			return new PaymentProviderUPIRequest(paymentDto.getProvider(), paymentDto.getAmount(),
					paymentDto.getVirtualPaymentAddress(), paymentDto.getUserComments());
		default:
			return null;
		}

	}

}

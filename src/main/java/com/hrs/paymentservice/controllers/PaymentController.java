package com.hrs.paymentservice.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrs.paymentservice.messaging.TopicProducer;
import com.hrs.paymentservice.models.PaymentDto;
import com.hrs.paymentservice.services.PaymentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(produces = "application/json", value = "Operations pertaining to manage payments in hotel reservation system")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {

	private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private PaymentService paymentService;

	private TopicProducer topicProducer;

	public PaymentController(TopicProducer producer) {
		this.topicProducer = producer;
	}

	@GetMapping("/welcome")
	private ResponseEntity<String> displayWelcomeMessage() {
		return new ResponseEntity<>("Welcome to payment service !!", HttpStatus.OK);
	}

	@PostMapping("/pay")
	public ResponseEntity<PaymentDto> processPayment(@RequestBody @Valid PaymentDto payment) {
		PaymentDto processedPayment = paymentService.processTransaction(payment);

		log.info("Payment done successfully hence publishing the payment event.");
		topicProducer.sendPayment(processedPayment);

		return new ResponseEntity<>(processedPayment, HttpStatus.CREATED);
	}

	@PostMapping("/refund")
	public ResponseEntity<PaymentDto> processRefund(@RequestBody @Valid PaymentDto payment) {
		PaymentDto processedPayment = paymentService.processTransaction(payment);

		log.info("Refund done successfully hence publishing the refund event.");
		topicProducer.sendRefund(processedPayment);

		return new ResponseEntity<>(processedPayment, HttpStatus.OK);
	}

	@GetMapping("/retrieve/{id}")
	@ApiOperation(value = "Retrieve the payment with the specified id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved payment"),
			@ApiResponse(code = 404, message = "Payment with specified id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<PaymentDto> getPaymentById(@PathVariable("id") @Valid long id) {
		PaymentDto payment = paymentService.getPaymentById(id);
		return new ResponseEntity<>(payment, HttpStatus.OK);
	}

	@GetMapping("/retrieve/customer/{id}")
	@ApiOperation(value = "Retrieve the payments with the specified customer id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved payments"),
			@ApiResponse(code = 404, message = "Payments for the specified customer id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<List<PaymentDto>> getPaymentByCustomerId(@PathVariable("id") @Valid long id) {
		List<PaymentDto> payments = paymentService.getPaymentsByCustomerId(id);
		return new ResponseEntity<>(payments, HttpStatus.OK);
	}

}

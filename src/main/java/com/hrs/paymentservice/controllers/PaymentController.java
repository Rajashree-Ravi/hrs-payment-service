package com.hrs.paymentservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PaymentController {

	@GetMapping("/welcome")
	private ResponseEntity<String> displayWelcomeMessage() {
		return new ResponseEntity<>("Welcome to payment service !!", HttpStatus.OK);
	}

}

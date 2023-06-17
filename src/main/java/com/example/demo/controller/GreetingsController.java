package com.example.demo.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greeting")
public class GreetingsController {


	@GetMapping
	public ResponseEntity<String> sayHello(Principal principal) {
		return ResponseEntity.ok("Hello from our API " + principal.getName());
	}
	
	@GetMapping("/say-good-bye")
	public ResponseEntity<String> sayGoodBye() {
		return ResponseEntity.ok("Goodbye and see you later!");
	}
}

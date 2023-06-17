package com.example.demo.security;

import lombok.Data;

@Data
public class AuthenticationRequest {

	private Object email;
	private Object password;
}

package com.example.vaccination_app.exception;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException() {}

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}

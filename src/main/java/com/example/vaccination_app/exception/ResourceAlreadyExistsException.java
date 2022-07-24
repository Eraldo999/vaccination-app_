package com.example.vaccination_app.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
	public ResourceAlreadyExistsException() {}

	public ResourceAlreadyExistsException(String msg) {
		super(msg);
	}
}

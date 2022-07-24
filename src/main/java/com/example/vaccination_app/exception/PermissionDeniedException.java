package com.example.vaccination_app.exception;

public class PermissionDeniedException extends IllegalStateException {
	public PermissionDeniedException() {
		super();
	}

	public PermissionDeniedException(String s) {
		super(s);
	}
}

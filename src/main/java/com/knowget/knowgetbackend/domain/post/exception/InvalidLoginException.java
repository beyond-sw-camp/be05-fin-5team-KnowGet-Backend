package com.knowget.knowgetbackend.domain.post.exception;

public class InvalidLoginException extends RuntimeException {
	public InvalidLoginException(String message) {
		super(message);
	}
}

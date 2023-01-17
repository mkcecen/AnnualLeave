package com.annualLeave.framework.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthenticateException extends Exception {

	private static final long serialVersionUID = 1L;

	public AuthenticateException() {}
	
	public AuthenticateException(String message) {
		super(message);
	}
}

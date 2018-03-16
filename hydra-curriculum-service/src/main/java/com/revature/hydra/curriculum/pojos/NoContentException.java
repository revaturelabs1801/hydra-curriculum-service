package com.revature.hydra.curriculum.pojos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1669307485951446112L;

	private final String message;

	public NoContentException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}

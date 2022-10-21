package com.udacity.jdnd.course3.critter.exception;

public class PersonAlreadyExistsException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonAlreadyExistsException(String message) {
		super(message);
	}

}

package com.axatrikx.exceptions;

/**
 * Exceptions related to Object Repository.
 * 
 * @author amalbose
 *
 */
public class RepositoryException extends RuntimeException {

	private static final long serialVersionUID = -2507237644794725641L;

	public RepositoryException(String message) {
		super(message);
	}
}

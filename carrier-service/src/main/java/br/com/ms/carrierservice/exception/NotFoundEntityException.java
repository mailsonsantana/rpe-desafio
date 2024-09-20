package br.com.ms.carrierservice.exception;

public class NotFoundEntityException extends RuntimeException {
	public NotFoundEntityException() {
		super();
	}

	public NotFoundEntityException(String s) {
		super(s);
	}
}

package br.com.ms.carrierservice.exception;

public class JwtInvalidoException extends RuntimeException {
	public JwtInvalidoException() {
		super();
	}

	public JwtInvalidoException(String s) {
		super(s);
	}
}

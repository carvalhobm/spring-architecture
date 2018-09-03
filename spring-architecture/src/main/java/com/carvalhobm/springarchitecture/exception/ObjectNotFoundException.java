package com.carvalhobm.springarchitecture.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String id, Class<?> clazz) {
		super("Objeto não encontrado! ID: " + id + ", Tipo do Objeto: " + clazz.getName());
	}

	public ObjectNotFoundException(String msg) {
		super(msg);
	}

	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

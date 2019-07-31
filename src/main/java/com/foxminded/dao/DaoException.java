package com.foxminded.dao;

public class DaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public DaoException() {
		super();
	}
	
	public DaoException(String message, Throwable error) {
		super(message, error);
	}
}

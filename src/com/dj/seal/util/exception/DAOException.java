package com.dj.seal.util.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DAOException extends RuntimeException {
	
	static Logger logger = LogManager.getLogger(DAOException.class.getName());

	private static final long serialVersionUID = 216937704935299627L;

	public DAOException() {
		super();
	}
	public DAOException(String message) {
		super(message);
	}	
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
	public DAOException(Throwable cause) {
		super(cause);
	}

}

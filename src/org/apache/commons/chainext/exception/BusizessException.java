package org.apache.commons.chainext.exception;

/**
 * 业务异常
 */
public class BusizessException extends RuntimeException {

	private static final long serialVersionUID = 4659413972311969823L;

	private Object errorCode; //错误代码
	
	/*
	 * =====================
	 *constructor
	 */
	public BusizessException() {
		super();
	}
	public BusizessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public BusizessException(String message, Throwable cause) {
		super(message, cause);
	}
	public BusizessException(String message) {
		super(message);
	}
	public BusizessException(Throwable cause) {
		super(cause);
	}
	public BusizessException(String message,Object errorCode) {
		this(message);
		this.errorCode = errorCode;
	}
	public BusizessException(Object errorCode) {
		this(null,errorCode);
	}

	
	
	public Object getErrorCode() {
		return errorCode;
	}
}

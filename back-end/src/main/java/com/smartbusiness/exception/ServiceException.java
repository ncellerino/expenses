package com.smartbusiness.exception;

/**
 * Useful class to have a unique way of sending back error messages.
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 2996658396059508029L;

	private String details;
	private ErrorResponseEnum errorResponse;
	private Throwable exception;

	public ServiceException(ErrorResponseEnum errorResponse, String details) {
		super(errorResponse.getHttpStatus() + ". " + errorResponse.getMessage() + ". " + details);
		this.errorResponse = errorResponse;
		this.details = details;
	}

	public ServiceException(ErrorResponseEnum errorResponse, Throwable exception) {
		this(errorResponse, exception.getMessage());
		this.exception = exception;
	}

	public ErrorResponseEnum getErrorResponse() {
		return errorResponse;
	}

	public Throwable getException() {
		return exception;
	}

	public String getDetails() {
		return details;
	}
}

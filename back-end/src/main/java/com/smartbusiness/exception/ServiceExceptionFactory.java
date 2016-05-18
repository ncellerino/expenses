package com.smartbusiness.exception;


/**
 * Helper class to create instance of {@link ServiceException}.
 */
public class ServiceExceptionFactory {

	public static ServiceException build(ErrorResponseEnum errorResponse, String details) {
		return new ServiceException(errorResponse, details);
	}

	public static ServiceException build(ErrorResponseEnum errorResponse, Throwable ex) {
		return new ServiceException(errorResponse, ex);
	}
}

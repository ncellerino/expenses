package com.expenses.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<String> handleBadRequestException(BadRequestException bre, HttpServletRequest request,
			HttpServletResponse resp) throws IOException {
		resp.sendError(HttpStatus.BAD_REQUEST.value(), bre.getMessage());
		return ResponseEntity.badRequest().body(bre.getMessage());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseBody
	public void handleDuplicateKeyException(DuplicateKeyException dke, HttpServletRequest request,
			HttpServletResponse resp) throws IOException {
		resp.sendError(HttpStatus.CONFLICT.value(), dke.getMessage());
	}

	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
	@ResponseBody
	public void handleAuthenticationException(AuthenticationCredentialsNotFoundException cnfe,
			HttpServletRequest request, HttpServletResponse resp) throws IOException {
		resp.sendError(HttpStatus.UNAUTHORIZED.value(), cnfe.getMessage());
	}

}

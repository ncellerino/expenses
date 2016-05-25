package com.expenses.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {

	@Autowired
	private JwtAuthenticationService jwtAuthenticationService;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse httpResponse = (HttpServletResponse) res;
		Authentication authentication = jwtAuthenticationService.getAuthentication(httpRequest);
		if(authentication == null){
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	public void setJwtAuthenticationService(JwtAuthenticationService jwtAuthenticationService) {
		this.jwtAuthenticationService = jwtAuthenticationService;
	}
}

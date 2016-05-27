package com.expenses.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.expenses.model.AuthenticatedUser;
import com.expenses.model.BaseUser;

@Component
public class JwtAuthenticationService {

	private static final String AUTH_HEADER_NAME = "Authorization";

	@Autowired
	private final JwtTokenHandler tokenHandler;

	public JwtAuthenticationService() {
		tokenHandler = new JwtTokenHandler();
	}

//	public void addAuthentication(HttpServletResponse response, AuthenticatedUser authentication) {
//		final User user = (User) authentication.getDetails();
//		response.addHeader(AUTH_HEADER_NAME, tokenHandler.generateToken(user));
//	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String header = request.getHeader(AUTH_HEADER_NAME);
//		if (header == null || !header.startsWith("Bearer ")) {
//			throw new AuthenticationCredentialsNotFoundException("No JWT token found in request headers");
//		}
		if(header != null && header.startsWith("Bearer ")){
			String authToken = header.substring(7);
			if (authToken != null) {
				final BaseUser user = tokenHandler.parseToken(authToken);
				if (user != null) {
					List<GrantedAuthority> authorityList = AuthorityUtils
							.commaSeparatedStringToAuthorityList(user.getRole());

					new org.springframework.security.core.userdetails.User(user.getId(), user.getUsername(), authorityList);
					return new AuthenticatedUser(user, authToken, authorityList);
				}
			}
		}
		
		return null;
	}

}

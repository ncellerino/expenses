package com.expenses.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.expenses.config.JwtConfigurer;
import com.expenses.model.BaseUser;
import com.expenses.model.User;

public class JwtTokenHandlerTest {

	private static String SHARED_KEY = "shared-key";
	private static String SUBJECT = "subject";
	private static String ISSUER = "issuer";
	private static long TOKEN_DURATION = 12000;

	JwtTokenHandler handler = new JwtTokenHandler();

	@Mock
	JwtConfigurer jwtConfigurer;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		handler.setJwtConfigurer(jwtConfigurer);
	}

	@Test
	public void generateToken() {
		Mockito.when(jwtConfigurer.getSharedSecretKey()).thenReturn(SHARED_KEY);
		Mockito.when(jwtConfigurer.getSubject()).thenReturn(SUBJECT);
		Mockito.when(jwtConfigurer.getIssuer()).thenReturn(ISSUER);
		Mockito.when(jwtConfigurer.getTokenDuration()).thenReturn(TOKEN_DURATION);

		String token = handler.generateToken(getDefaultUser());

		Assert.assertNotNull(token);
	}

	@Test
	public void parseToken() {
		Mockito.when(jwtConfigurer.getSharedSecretKey()).thenReturn(SHARED_KEY);
		Mockito.when(jwtConfigurer.getSubject()).thenReturn(SUBJECT);
		Mockito.when(jwtConfigurer.getIssuer()).thenReturn(ISSUER);
		Mockito.when(jwtConfigurer.getTokenDuration()).thenReturn(TOKEN_DURATION);

		User user = getDefaultUser();
		String token = handler.generateToken(user);

		BaseUser baseUser = handler.parseToken(token);
		Assert.assertNotNull(baseUser);
		Assert.assertEquals(user.getUsername(), baseUser.getUsername());
		Assert.assertEquals(user.getRole(), baseUser.getRole());
	}

	private User getDefaultUser() {
		return new User.UserBuilder(null, "batman@gmail.com", "batman", "admin", "Bruce", "Wayne", null, null)
				.address("123 fake street, gotham").phone("13234534").age(32).build();
	}

}

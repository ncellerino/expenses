package com.expenses.security;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.expenses.config.JwtConfigurer;
import com.expenses.model.BaseUser;
import com.expenses.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHandler {

	@Autowired
	JwtConfigurer jwtConfigurer;

	/**
	 * The JWT signature algorithm we will be using to sign the token
	 */
	private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

	private Log log = LogFactory.getLog(JwtTokenHandler.class);

	/**
	 * Tries to parse specified String as a JWT token. If successful, returns
	 * User object with username, id and role prefilled (extracted from token).
	 * If unsuccessful (token is invalid or not containing all required user
	 * properties), simply returns null.
	 * 
	 * @param token
	 *            the JWT token to parse
	 * @return the User object extracted from specified token or null if a token
	 *         is invalid.
	 */
	public BaseUser parseToken(String token) {
		// We will sign our JWT with our ApiKey secret
		Key signingKey = new SecretKeySpec(jwtConfigurer.getSharedSecretKey().getBytes(),
				signatureAlgorithm.getJcaName());

		try {
			Claims body = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
			Date now = new Date();
			long expirationMillis = (long) body.get("exp");
			Date expiration = new Date(expirationMillis);
			BaseUser u = null;
			if (now.before(expiration)) {
				// token is valid
				u = new BaseUser((String) body.get("userId"), body.getSubject(), (String) body.get("role"));
			} else {
				log.debug("The token has expired");
			}
			return u;
		} catch (JwtException | ClassCastException cce) {
			log.debug("The token couldn't be parsed", cce);
			return null;
		}
	}

	/**
	 * Generates a JWT token containing username as subject, and userId and role
	 * as additional claims. These properties are taken from the specified User
	 * object. Tokens validity is infinite.
	 * 
	 * @param u
	 *            the user for which the token will be generated
	 * @return the JWT token
	 */
	public String generateToken(User u) {
		String sharedSecret = jwtConfigurer.getSharedSecretKey();
		String subject = jwtConfigurer.getSubject();
		String issuer = jwtConfigurer.getIssuer();
		long tokenDuration = jwtConfigurer.getTokenDuration();

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		Key signingKey = new SecretKeySpec(sharedSecret.getBytes(), signatureAlgorithm.getJcaName());

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString()).setIssuedAt(now).setSubject(subject)
				.setIssuer(issuer).signWith(signatureAlgorithm, signingKey);

		Claims claims = Jwts.claims().setSubject(u.getUsername());
		claims.put("userId", u.getUsername() + "");
		claims.put("role", u.getRole());
		if (tokenDuration > 0) {
			long expMillis = nowMillis + tokenDuration;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
			claims.put("exp", exp.getTime());
		}

		return builder.setClaims(claims).compact();
	}

	public void setJwtConfigurer(JwtConfigurer jwtConfigurer) {
		this.jwtConfigurer = jwtConfigurer;
	}
}

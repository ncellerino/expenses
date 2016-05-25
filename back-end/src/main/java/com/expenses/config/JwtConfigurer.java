package com.expenses.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtConfigurer {

	private String sharedSecretKey;
	private String issuer;
	private String subject;
	private long tokenDuration;

	public String getSharedSecretKey() {
		return sharedSecretKey;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public long getTokenDuration() {
		return tokenDuration;
	}

	public void setTokenDuration(long tokenDuration) {
		this.tokenDuration = tokenDuration;
	}

	public void setSharedSecretKey(String sharedSecretKey) {
		this.sharedSecretKey = sharedSecretKey;
	}

}

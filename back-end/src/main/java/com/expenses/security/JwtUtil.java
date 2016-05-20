package com.expenses.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.expenses.model.BaseUser;
import com.expenses.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

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
        try {
            Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();          
            Date now = new Date();
            long expirationMillis = (long) body.get("exp");
            Date expiration = new Date(expirationMillis);
            BaseUser u = null;
            if (now.before(expiration)) {
                // token is valid            	
                u = new BaseUser((String)body.get("userId"), body.getSubject(), (String) body.get("role"));
            } else {
                LOG.debug("The token has expired");
            }
            return u;
        } catch (JwtException | ClassCastException cce) {
            LOG.debug("The token couldn't be parsed", cce);
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
    public String generateToken(User u, long tokenDuration) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("userId", u.getId() + "");
        claims.put("role", u.getRole());
        JwtBuilder builder = Jwts.builder();
        if (tokenDuration > 0) {
            long nowMillis = System.currentTimeMillis();

            long expMillis = nowMillis + tokenDuration;
            Date exp = new Date(expMillis);
            claims.put("exp", exp.getTime());
        }

        return builder.setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}

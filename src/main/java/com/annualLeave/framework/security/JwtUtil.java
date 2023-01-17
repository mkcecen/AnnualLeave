package com.annualLeave.framework.security;

import com.annualLeave.entity.Person;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	public String encrypt(Person user) {
		Claims claims = Jwts.claims().setSubject(String.valueOf(user.getId()));
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Person decrypt(String token) {
		Person pereson = null;
		try {
			Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			pereson = new Person();
			pereson.setId(Long.valueOf(body.getSubject()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pereson;
	}
}
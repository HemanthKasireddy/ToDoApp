package com.bridgeit.toDoNotes.tokens;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class TokenImpl implements ITokens {
	private Key key = MacProvider.generateKey();

	@Override
	public String generateToken(String subject,String id) {
		Calendar calendar = Calendar.getInstance();
		Date currentTime = calendar.getTime();
		calendar.add(Calendar.MINUTE, 15);
		Date expirationDate = calendar.getTime();
		String token = Jwts.builder()
				.setSubject(subject)
				.setId(id)
				.setExpiration(expirationDate)
				.setIssuedAt(currentTime)
				.signWith(SignatureAlgorithm.HS512, key)
				.compact();
		System.out.println("@@@@@@@@@@@@@@@@@@ created token is ###################### "+token);
		return token ;
	}

	@Override
	public String verifyToken(String token) {

		try {
			 Claims claims = Jwts.parser()
					 .setSigningKey(key)
					 .parseClaimsJws(token).getBody();
			System.out.println("Token is ok");
			return claims.getId();
		} catch (Exception ex) {
				System.out.println(token);
				ex.printStackTrace();
				return null;
			}
	}
}

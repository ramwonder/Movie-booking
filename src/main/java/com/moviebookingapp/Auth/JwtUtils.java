package com.moviebookingapp.Auth;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.moviebookingapp.exception.tokenUnauthorizedException;
import com.moviebookingapp.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	
	private static String secret="This_is_secret";
	private static long expiryDuration=60*60;
	public String generateJwt(String loginId)

	{
		long milliTime=System.currentTimeMillis();
		long expiryTime=milliTime*expiryDuration*1000;
		Date issuedAt=new Date(milliTime);
		
		Date expiryAt= new Date(expiryTime);
		Claims claims=Jwts.claims().setIssuer(loginId)
				.setIssuedAt(issuedAt)
				.setExpiration(expiryAt);
		
		return  Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512,secret).compact().toString();
	}
	
	public void verify(String authorization) throws Exception
	{
		try {
		Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization);
		}
		catch(Exception e){
			throw new tokenUnauthorizedException("token is not vaild");
		}
	}

}

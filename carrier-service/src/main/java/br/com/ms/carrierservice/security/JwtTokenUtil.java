package br.com.ms.carrierservice.security;

import br.com.ms.carrierservice.exception.JwtInvalidoException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = -2550185165626007488L;
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
		
	@Value("${carrier.keysecret}")
	private String secret;

	public User getUserSalesForce(String token) {
		try{
			final Claims claims = getAllClaimsFromToken(token);
			User userSalesforce = new User();
			userSalesforce.setCpf(claims.get("cpf").toString());
			return userSalesforce;
		}catch(NoSuchAlgorithmException ex) {
			throw new JwtInvalidoException(ex.getMessage());
		}
		
	}
	
	public String generateKey() throws NoSuchAlgorithmException {
		SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
		
		// get base64 encoded version of the key
		return Base64.getEncoder().encodeToString(secretKey.getEncoded());
		
	}

	public Claims getAllClaimsFromToken(String token) throws NoSuchAlgorithmException{
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	public Date getExpirationDateFromToken(String token) throws ExpiredJwtException, NoSuchAlgorithmException{
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws ExpiredJwtException, NoSuchAlgorithmException{
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(Map<String, Object> claims) {
		return doGenerateToken(claims);
	}
	
	private String doGenerateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}
	
	public boolean validateToken(String token) throws ExpiredJwtException{
		if(!StringUtils.isBlank(token) && token.contains("Bearer")){
			token = token.substring(7);
		}
		return !StringUtils.isBlank(getUserSalesForce(token).getCpf());
	}
	
	
	
}
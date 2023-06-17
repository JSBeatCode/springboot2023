package com.example.demo.security;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

	private String SECRET_KEY = "jsdno0SecretKey20230612IdontKnowWhatsGoingOn";
	
    /**
     * 토큰에서 사용자 이름을 추출합니다.
     *
     * @param token 토큰
     * @return 사용자 이름
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 토큰에서 만료 날짜를 추출합니다.
     *
     * @param token 토큰
     * @return 만료 날짜
     */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
    /**
     * 토큰에서 클레임을 추출합니다.
     *
     * @param token           토큰
     * @param claimsResolver  클레임 리졸버 함수
     * @param <T>             추출할 값의 타입
     * @return 추출된 클레임 값
     */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
    /**
     * 토큰에서 모든 클레임을 추출합니다.
     *
     * @param token 토큰
     * @return 추출된 클레임
     */
	private Claims extractAllClaims(String token) {
//		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(token).getBody();
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).after(new Date());
//		return extractExpiration(token).before(new Date());
	}
	
	 /**
     * 사용자 정보를 기반으로 토큰을 생성합니다.
     *
     * @param userDetails 사용자 정보
     * @return 생성된 토큰
     */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails);
	}
	
	public String generateToken(UserDetails userDetails, Map<String, Object> claims) {
		return createToken(claims, userDetails);
	}
	
	private String createToken(Map<String, Object> claims, UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.claim("authorities", userDetails.getAuthorities())
				.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 10)))
					.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
    /**
     * 토큰의 유효성을 검증합니다.
     *
     * @param token        토큰
     * @param userDetails 사용자 정보
     * @return 토큰의 유효성 여부
     */
	public boolean validateToken(String token, UserDetails userdetails) {
		final String username = extractUsername(token);
		return (username.equals(userdetails.getUsername()) && !isTokenExpired(token));
	}

}

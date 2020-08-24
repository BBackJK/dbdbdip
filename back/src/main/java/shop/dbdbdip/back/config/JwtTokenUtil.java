package shop.dbdbdip.back.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
	
	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	@Value("${jwt.secret}")
	private String secret;
	
	// 사용자에 대한 토큰 생성
		public String generateToken(UserDetails userDetails) {
			
			Map<String, Object> claims = new HashMap<> ();
			
			return doGenerateToken(claims, userDetails.getUsername());
		}
		
		// 토큰을 만드는 과정
		// 1. ID, Subject, Expiration과 Issuer와 같은 토큰의 클레임을 정의
		// 2. HS512 알고리즘과 비밀 키를 사용하여 JWT에 서명
		// 3. WS Compact Serialization (https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)에 따름
		// 4. JWT를 URL 안전 문자열로 압축
		private String doGenerateToken(Map<String, Object> claims, String sub) {
			
			return Jwts.builder().setClaims(claims).setSubject(sub).setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
					.signWith(SignatureAlgorithm.HS512, secret).compact();
		}
		
		// token으로 정보를 얻기 위해서는 secret 키가 필요하다.
		private Claims getAllClaimsFromToken(String token) {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		}
		
		// JWT token으로 부터 Claim 정보를 검색
		public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
			
			final Claims claims = getAllClaimsFromToken(token);
			
			return claimsResolver.apply(claims);
		}
		
		// JWT token으로 부터 username(email) 검색
		public String getUsernameFromToken(String token) {
			return getClaimFromToken(token, Claims::getSubject);
		}
		
		// JWT token으로 부터 expiration date (만료일자) 검색
		public Date getExpirationDateFromToken(String token) {
			return getClaimFromToken(token, Claims::getExpiration);
		}
		
		// JWT 토큰이 만료되었는지 확인
		private Boolean isTokenExpired(String token) {
			final Date expiration = getExpirationDateFromToken(token);
			return expiration.before(new Date());
		}
		
		// JWT token 유효성 확인
		public Boolean validateToken(String token, UserDetails userDetails) {
			final String username = getUsernameFromToken(token);
			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}
}

package shop.dbdbdip.back.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import shop.dbdbdip.back.service.UserServiceImpl;

@Component
public class AuthRequestFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public String doFilterInternal(HttpServletRequest request, HttpServletResponse response) {
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String email = null;
		String jwtToken = null;
		
		// JWT token은 bearer 타입. Bearer를 빼고 토큰만 가져온다.
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			
			jwtToken = requestTokenHeader.substring(7);
			
			try {
				email = jwtTokenUtil.getUsernameFromToken(jwtToken);
				
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}
		
		// token을 받으면 유효성 체크
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.userService.loadUserByUsername(email);
			
			// token이 유효한 경우 수동으로 설정하도록 Spring Security 구성 인증
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// Context에서 인증을 설정한 후
				// 현재 사용자가 인증됨을 나타낸다. 즉, Spring 보안 구성이 완료되었음을 알린다.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		return email;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");

		String email = null;
		String jwtToken = null;
		
		// JWT token은 bearer 타입. Bearer를 빼고 토큰만 가져온다.
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			
			jwtToken = requestTokenHeader.substring(7);
			
			try {
				email = jwtTokenUtil.getUsernameFromToken(jwtToken);
				
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}
		
		// token을 받으면 유효성 체크
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.userService.loadUserByUsername(email);
			
			// token이 유효한 경우 수동으로 설정하도록 Spring Security 구성 인증
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// Context에서 인증을 설정한 후
				// 현재 사용자가 인증됨을 나타낸다. 즉, Spring 보안 구성이 완료되었음을 알린다.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
}

package shop.dbdbdip.back.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import shop.dbdbdip.back.service.UserServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthEntryPoint authEntryPoint;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private AuthRequestFilter authRequestFilter;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		// 어디에 로드하는 지 알 수 있도록 AuthenticationManagerBuilder를 구성한다.
		// 자격 증명 일치를 위한 유저와
		// BCryptPasswordEncoder 사용
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		// csrf 사용 x --> rest api이므로
		httpSecurity.csrf().disable()
		
			// 나열한 url에 대한 인증요청은 하지 않는다.
//			.authorizeRequests().antMatchers("*").permitAll().and()
//			
//			// 나머지 url에 대한 인증 요청은 한다.
//			.anyRequest().authenticated().and()
			
			// stateless 이므로 세션사용 x
			// 사용자의 상태를 저장
			.exceptionHandling().authenticationEntryPoint(authEntryPoint).and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// 모든 요청에서 토큰의 유효성을 검사하는 필터 추가
		httpSecurity.addFilterBefore(authRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}

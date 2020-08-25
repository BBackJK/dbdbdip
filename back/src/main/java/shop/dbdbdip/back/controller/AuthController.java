package shop.dbdbdip.back.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import shop.dbdbdip.back.config.AuthRequestFilter;
import shop.dbdbdip.back.config.JwtTokenUtil;
import shop.dbdbdip.back.dto.ResponseMessage;
import shop.dbdbdip.back.dto.auth.AuthPostDto;
import shop.dbdbdip.back.dto.user.UserResponseDto;
import shop.dbdbdip.back.service.UserServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private AuthRequestFilter authFilter;
	
	@Autowired
	private UserServiceImpl userService;
	
	@RequestMapping(value= "/login", method=RequestMethod.POST)
	public ResponseMessage authLogin(@RequestBody AuthPostDto authDto) throws Exception {
		
		auth(authDto.getEmail(), authDto.getPassword());
		
		final UserDetails userDetails = userService.loadUserByUsername(authDto.getEmail());
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		UserResponseDto userInfo = userService.readUserByEmail(authDto.getEmail());
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("user", userInfo);
		message.add("token", token);
		
		return message;
	}
	
	@RequestMapping(value="/me", method=RequestMethod.GET)
	public ResponseMessage getUserByEmail(HttpServletRequest request, HttpServletResponse response) {
		
		String email = authFilter.doFilterInternal(request, response);
		
		UserResponseDto user = userService.readUserByEmail(email);
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("user", user);
		
		return message;				
	}
	
	private void auth(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}

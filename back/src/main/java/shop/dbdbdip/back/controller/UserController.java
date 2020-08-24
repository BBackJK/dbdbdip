package shop.dbdbdip.back.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.ExpiredJwtException;
import shop.dbdbdip.back.config.JwtTokenUtil;
import shop.dbdbdip.back.dto.ResponseMessage;
import shop.dbdbdip.back.model.user.UserPostDto;
import shop.dbdbdip.back.model.user.UserResponseDto;
import shop.dbdbdip.back.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseMessage getUserById(@RequestParam("id") int id) {
		
		UserResponseDto user = userService.getUserById(id);
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("user", user);
		
		return message;
	}
	
	@RequestMapping(value="/info", method=RequestMethod.GET)
	public ResponseMessage getUserByEmail(HttpServletRequest request) {
		
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String email = null;
		String jwtToken = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			
			jwtToken = requestTokenHeader.substring(7);
			
			System.out.println(jwtToken);
			
			try {
				email = jwtTokenUtil.getUsernameFromToken(jwtToken);
				
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			System.out.println("JWT Token does not begin with Bearer String");
		} 
		
		UserResponseDto user = userService.getUserByEmail(email);
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("user", user);
		
		return message;
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseMessage postUser(@RequestBody UserPostDto user) {
		
		int result = userService.postUser(user);
		
		if (result != 1) {
			ResponseMessage message = new ResponseMessage(HttpStatus.CONFLICT);
			
			return message;
		}
		
		ResponseMessage message = new ResponseMessage(HttpStatus.CREATED);
		
		return message;		
	}
}

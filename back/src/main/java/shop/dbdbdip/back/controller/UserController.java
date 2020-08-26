package shop.dbdbdip.back.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shop.dbdbdip.back.config.AuthRequestFilter;
import shop.dbdbdip.back.dto.ResponseMessage;
import shop.dbdbdip.back.dto.user.UserPostDto;
import shop.dbdbdip.back.dto.user.UserResponseDto;
import shop.dbdbdip.back.dto.user.UserUpdateDto;
import shop.dbdbdip.back.dto.user.UserUpdatePasswordDto;
import shop.dbdbdip.back.model.user.UserResponseModel;
import shop.dbdbdip.back.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthRequestFilter authFilter;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseMessage get(@RequestParam("id") int id) {
		
		UserResponseDto user = userService.readUserById(id);
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("user", user);
		
		return message;
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseMessage post(@RequestBody UserPostDto user) {
		
		int result = userService.createUser(user);
		
		if (result != 1) {
			ResponseMessage message = new ResponseMessage(HttpStatus.CONFLICT);
			
			return message;
		}
		
		ResponseMessage message = new ResponseMessage(HttpStatus.CREATED);
		
		return message;		
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseMessage update(@RequestBody UserUpdateDto user) {
		
		UserResponseModel result = userService.updateUser(user);
		
		if (result == null) {
			ResponseMessage message = new ResponseMessage(HttpStatus.CONFLICT);
			
			return message;
		}
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("user", result);
		
		return message;
	}
	
	@RequestMapping(value="/password", method=RequestMethod.PUT)
	public ResponseMessage updatePassword(@RequestBody UserUpdatePasswordDto user) {
		
		UserResponseModel result = userService.updateUserPassword(user);
		
		if (result == null) {
			ResponseMessage message = new ResponseMessage(HttpStatus.CONFLICT);
			
			return message;
		}
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("user", result);
		
		return message;
	}
	
	@RequestMapping(value="/remove",method=RequestMethod.DELETE)
	public ResponseMessage delete(HttpServletRequest request, HttpServletResponse response) {
		
		String email = authFilter.doFilterInternal(request, response);
		
		int result = userService.deleteUser(email);
		
		if (result != 1) {
			
			ResponseMessage message = new ResponseMessage(HttpStatus.CONFLICT);
			
			return message;
		}
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		return message;
	}
}

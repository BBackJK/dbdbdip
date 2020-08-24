package shop.dbdbdip.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseMessage getUserById(@RequestParam("id") int id) {
		
		UserResponseDto user = userService.getUserById(id);
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
		message.add("user", user);
		
		return message;
	}
	
	@RequestMapping(value="info", method=RequestMethod.GET)
	public ResponseMessage getUserByEmail() {
		
		ResponseMessage message = new ResponseMessage(HttpStatus.OK);
		
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

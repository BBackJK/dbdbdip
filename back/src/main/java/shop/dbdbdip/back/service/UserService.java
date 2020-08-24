package shop.dbdbdip.back.service;

import shop.dbdbdip.back.model.user.UserPostDto;
import shop.dbdbdip.back.model.user.UserResponseDto;

public interface UserService {

	int postUser(UserPostDto user);
	
	UserResponseDto getUserById(int id);
	
	UserResponseDto getUserByEmail(String email);
}

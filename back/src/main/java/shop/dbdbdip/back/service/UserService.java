package shop.dbdbdip.back.service;

import shop.dbdbdip.back.dto.user.UserPostDto;
import shop.dbdbdip.back.dto.user.UserResponseDto;
import shop.dbdbdip.back.dto.user.UserUpdateDto;
import shop.dbdbdip.back.dto.user.UserUpdatePasswordDto;
import shop.dbdbdip.back.model.user.UserResponseModel;

public interface UserService {
	
	UserResponseDto readUserById(int id);
	
	UserResponseDto readUserByEmail(String email);
	
	int createUser(UserPostDto user);
	
	UserResponseModel updateUser(UserUpdateDto user);
	
	UserResponseModel updateUserPassword(UserUpdatePasswordDto user);
	
	int deleteUser(String email);
}	

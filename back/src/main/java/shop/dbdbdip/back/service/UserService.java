package shop.dbdbdip.back.service;

import shop.dbdbdip.back.dto.user.UserPostDto;
import shop.dbdbdip.back.dto.user.UserResponseDto;
import shop.dbdbdip.back.dto.user.UserUpdateDto;
import shop.dbdbdip.back.dto.user.UserUpdatePasswordDto;

public interface UserService {
	
	UserResponseDto readUserById(int id);
	
	UserResponseDto readUserByEmail(String email);
	
	int createUser(UserPostDto user);
	
	int updateUser(UserUpdateDto user);
	
	int updateUserPassword(UserUpdatePasswordDto user);
	
	int deleteUser(String email);
}	

package shop.dbdbdip.back.mapper;

import shop.dbdbdip.back.model.UserModel;
import shop.dbdbdip.back.model.user.UserPostDto;
import shop.dbdbdip.back.model.user.UserResponseDto;

public interface UserMapper {
	
	UserResponseDto getById(int id);
	
	UserModel findByEmail(String email);
	
	UserResponseDto findByEmailNoPassword(String email);
	
	int saveUser(UserPostDto user);
	
}

package shop.dbdbdip.back.mapper;

import shop.dbdbdip.back.model.user.UserCreateModel;
import shop.dbdbdip.back.model.user.UserDeleteModel;
import shop.dbdbdip.back.model.user.UserModel;
import shop.dbdbdip.back.model.user.UserResponseModel;
import shop.dbdbdip.back.model.user.UserUpdateModel;
import shop.dbdbdip.back.model.user.UserUpdatePasswordModel;

public interface UserMapper {
	
	UserResponseModel getById(int id);
	
	UserModel getByEmail(String email);
	
	UserResponseModel getByEmailNoPassword(String email);
	
	int postUser(UserCreateModel user);
	
	int putUser(UserUpdateModel user);
	
	int putUserPassword(UserUpdatePasswordModel user);
	
	int deleteUser(UserDeleteModel user);
}

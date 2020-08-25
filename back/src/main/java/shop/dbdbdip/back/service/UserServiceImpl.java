package shop.dbdbdip.back.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import shop.dbdbdip.back.dto.user.UserPostDto;
import shop.dbdbdip.back.dto.user.UserResponseDto;
import shop.dbdbdip.back.dto.user.UserUpdateDto;
import shop.dbdbdip.back.dto.user.UserUpdatePasswordDto;
import shop.dbdbdip.back.mapper.UserMapper;
import shop.dbdbdip.back.model.user.UserModel;
import shop.dbdbdip.back.model.user.UserResponseModel;
import shop.dbdbdip.back.model.user.UserCreateModel;
import shop.dbdbdip.back.model.user.UserDeleteModel;
import shop.dbdbdip.back.model.user.UserUpdateModel;
import shop.dbdbdip.back.model.user.UserUpdatePasswordModel;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserResponseDto readUserById(int id) {
		
		UserResponseModel userModel = userMapper.getById(id);
		
		UserResponseDto user = new UserResponseDto();
		
		user.setId(userModel.getId());
		user.setEmail(userModel.getEmail());
		user.setName(userModel.getName());
		user.setPhone(userModel.getPhone());
		user.setZipcode(userModel.getZipcode());
		user.setAddress(userModel.getAddress());
		user.setCreatedAt(userModel.getCreatedAt());
		user.setUpdatedAt(userModel.getUpdatedAt());
		user.setDeletedAt(userModel.getDeletedAt());
		
		return user;
	}

	@Override
	public UserResponseDto readUserByEmail(String email) {
		
		UserResponseModel userModel = userMapper.getByEmailNoPassword(email);
		
		UserResponseDto user = new UserResponseDto();
		
		user.setId(userModel.getId());
		user.setEmail(userModel.getEmail());
		user.setName(userModel.getName());
		user.setPhone(userModel.getPhone());
		user.setZipcode(userModel.getZipcode());
		user.setAddress(userModel.getAddress());
		user.setCreatedAt(userModel.getCreatedAt());
		user.setUpdatedAt(userModel.getUpdatedAt());
		user.setDeletedAt(userModel.getDeletedAt());
		
		return user;
	}
	
	@Override
	public int createUser(UserPostDto user) {
		
		UserModel duplicatedUser = userMapper.getByEmail(user.getEmail());
		
		if (duplicatedUser != null) {
			return 0;
		}
		
		UserCreateModel post = new UserCreateModel();
		
		post.setEmail(user.getEmail());
		post.setPassword(bcryptEncoder.encode(user.getPassword()));
		post.setName(user.getName());
		post.setPhone(user.getPhone());
		post.setZipcode(user.getZipcode());
		post.setAddress(user.getAddress());
		
		int result = userMapper.postUser(post);
		
		return result;
	}
	
	@Override
	public int updateUser(UserUpdateDto user) {
		
		UserUpdateModel update = new UserUpdateModel();
		
		update.setEmail(user.getEmail());
		update.setName(user.getName());
		update.setPhone(user.getPhone());
		update.setZipcode(user.getZipcode());
		update.setAddress(user.getAddress());
		update.setUpdatedAt(this.getDate());
		
		int result = userMapper.putUser(update);
		
		return result;
	}

	@Override
	public int updateUserPassword(UserUpdatePasswordDto user) {
		
		UserModel checkUser = userMapper.getByEmail(user.getEmail());
		
		Boolean check = bcryptEncoder.matches(user.getOldPassword(), checkUser.getPassword());
		
		if (!check) return 0;
		
		UserUpdatePasswordModel update = new UserUpdatePasswordModel();
		
		update.setEmail(user.getEmail());
		update.setPassword(bcryptEncoder.encode(user.getNewPassword()));
		update.setUpdatedAt(this.getDate());
		
		int result = userMapper.putUserPassword(update);
				
		return result;
	}
	
	@Override
	public int deleteUser(String email) {
		
		UserDeleteModel delete = new UserDeleteModel();
		
		delete.setEmail(email);
		delete.setDeletedAt(this.getDate());
		
		int result = userMapper.deleteUser(delete);
		
		return result;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserModel user = userMapper.getByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + email);
		}
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				new ArrayList<>());
		}
	
	private String getDate() {
		
		Date today = new Date();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String date = format.format(today);
		
		return date;
	}
}

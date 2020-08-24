package shop.dbdbdip.back.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import shop.dbdbdip.back.mapper.UserMapper;
import shop.dbdbdip.back.model.UserModel;
import shop.dbdbdip.back.model.user.UserPostDto;
import shop.dbdbdip.back.model.user.UserResponseDto;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public int postUser(UserPostDto user) {
		
		UserModel duplicatedUser = userMapper.findByEmail(user.getEmail());
		
		if (duplicatedUser != null) {
			return 0;
		}
		
		UserPostDto beforeSavedUser = new UserPostDto();
		
		beforeSavedUser.setEmail(user.getEmail());
		beforeSavedUser.setName(user.getName());
		beforeSavedUser.setPhone(user.getPhone());
		beforeSavedUser.setAddress(user.getAddress());
		beforeSavedUser.setZipcode(user.getZipcode());
		beforeSavedUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		
		int result = userMapper.saveUser(beforeSavedUser);
		
		return result;
	}

	@Override
	public UserResponseDto getUserById(int id) {
		
		UserResponseDto user = userMapper.getById(id);
		
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserModel user = userMapper.findByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + email);
		}
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				new ArrayList<>());
		}

	@Override
	public UserResponseDto getUserByEmail(String email) {
		
		UserResponseDto user = userMapper.findByEmailNoPassword(email);
		
		return user;
	}
}

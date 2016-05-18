package com.smartbusiness.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbusiness.dto.UserDTO;
import com.smartbusiness.model.User;
import com.smartbusiness.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository repository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.repository = userRepository;
	}

	@Override
	public UserDTO saveUser(UserDTO userDTO) {
		byte[] password = null;
		byte[] salt = null;

		User user = new User.UserBuilder(userDTO.getId(), userDTO.getMail(), userDTO.getFirstName(),
				userDTO.getLastName(), password, salt).age(userDTO.getAge()).address(userDTO.getAddress())
						.phone(userDTO.getPhone()).build();
		user = repository.save(user);

		return toDto(user);
	}

	@Override
	public UserDTO getUserByMail(String mail) {
		User user = null;
		user = repository.findByMail(mail);
		return toDto(user);
	}

	@Override
	public UserDTO getUser(String id) {
		User user = repository.findOne(id);
		return toDto(user);
	}	

	@Override
	public void deleteUser(String id) {
		repository.delete(id);
	}
	
	private UserDTO toDto(User user) {
		return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getMail(), user.getAge(),
				user.getPhone(), user.getAddress());
	}

}

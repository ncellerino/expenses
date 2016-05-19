package com.expenses.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expenses.dto.UserDTO;
import com.expenses.dto.UserToSaveDTO;
import com.expenses.exception.ErrorResponseEnum;
import com.expenses.exception.ServiceExceptionFactory;
import com.expenses.model.User;
import com.expenses.repository.UserRepository;
import com.expenses.security.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.repository = userRepository;
	}

	@Override
	public UserDTO saveUser(UserToSaveDTO userDTO) {
		byte[] password = null;
		byte[] salt = null;

		try {
			salt = passwordEncoder.generateSalt();
			password = passwordEncoder.getEncryptedPassword(userDTO.getPassword(), salt);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw ServiceExceptionFactory.build(ErrorResponseEnum.INTERNAL_SERVER_ERROR,
					"There was a problem storing the user");
		}

		User user = new User.UserBuilder(userDTO.getId(), userDTO.getMail(), userDTO.getFirstName(),
				userDTO.getLastName(), password, salt).age(userDTO.getAge()).address(userDTO.getAddress())
						.phone(userDTO.getPhone()).build();
		user = repository.save(user);

		return toDto(user);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO) {
		User userToUpdate = repository.findOne(userDTO.getId());
		UserDTO updatedUserDTO = null;
		if (userToUpdate != null) {
			User user = null;
			user = new User.UserBuilder(userToUpdate.getId(), userDTO.getMail(), userDTO.getFirstName(),
					userDTO.getLastName(), userToUpdate.getPasswordHash(), userToUpdate.getPasswordSalt())
							.age(userDTO.getAge()).address(userDTO.getAddress()).phone(userDTO.getPhone()).build();

			user = repository.save(user);
			updatedUserDTO = toDto(user);
		}

		return updatedUserDTO;
	}

	@Override
	public UserDTO getUserByMail(String mail) {
		User user = null;
		user = repository.findByMail(mail);
		return toDto(user);
	}

	@Override
	public UserDTO getUser(String id) {
		UserDTO userDTO = null;
		User user = repository.findOne(id);
		if (user != null) {
			userDTO = toDto(user);
		}
		return userDTO;
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

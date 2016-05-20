package com.expenses.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expenses.dto.LoggedUserDTO;
import com.expenses.dto.UserDTO;
import com.expenses.dto.UserToSaveDTO;
import com.expenses.exception.ErrorResponseEnum;
import com.expenses.exception.ServiceExceptionFactory;
import com.expenses.model.User;
import com.expenses.repository.UserRepository;
import com.expenses.security.JwtUtil;
import com.expenses.security.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

	private long tokenDuration;

	@Autowired
	private final UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

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

		User user = new User.UserBuilder(userDTO.getId(), userDTO.getMail(), userDTO.getUsername(), userDTO.getRole(),
				userDTO.getFirstName(), userDTO.getLastName(), password, salt).age(userDTO.getAge())
						.address(userDTO.getAddress()).phone(userDTO.getPhone()).build();
		user = repository.save(user);

		return toDTO(user);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO) {
		User userToUpdate = repository.findOne(userDTO.getId());
		UserDTO updatedUserDTO = null;
		if (userToUpdate != null) {
			User user = null;
			user = new User.UserBuilder(userToUpdate.getId(), userDTO.getMail(), userDTO.getUsername(),
					userDTO.getRole(), userDTO.getFirstName(), userDTO.getLastName(), userToUpdate.getPasswordHash(),
					userToUpdate.getPasswordSalt()).age(userDTO.getAge()).address(userDTO.getAddress())
							.phone(userDTO.getPhone()).build();

			user = repository.save(user);
			updatedUserDTO = toDTO(user);
		}

		return updatedUserDTO;
	}

	@Override
	public LoggedUserDTO getUserByUsernameAndPassword(String username, String password) {
		User user = repository.findByUsername(username);
		boolean userValidated = false;
		LoggedUserDTO loggedUserDTO = null;
		if (user != null) {
			// check password
			try {
				userValidated = passwordEncoder.authenticate(password, user.getPasswordHash(), user.getPasswordSalt());
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				userValidated = false;
			}
			if (userValidated) {
				// generate JWT token
				String token = jwtUtil.generateToken(user, tokenDuration);
				loggedUserDTO = toLoggedUserDTO(user, token);
			}
		}

		return loggedUserDTO;
	}

	@Override
	public UserDTO getUserByMail(String mail) {
		User user = null;
		user = repository.findByMail(mail);
		return toDTO(user);
	}

	@Override
	public UserDTO getUser(String id) {
		UserDTO userDTO = null;
		User user = repository.findOne(id);
		if (user != null) {
			userDTO = toDTO(user);
		}
		return userDTO;
	}

	public void setJwtUtil(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setTokenDuration(long tokenDuration) {
		this.tokenDuration = tokenDuration;
	}

	@Override
	public void deleteUser(String id) {
		repository.delete(id);
	}

	private UserDTO toDTO(User user) {
		return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getMail(), user.getUsername(),
				user.getRole(), user.getAge(), user.getPhone(), user.getAddress());
	}

	private LoggedUserDTO toLoggedUserDTO(User user, String token) {
		return new LoggedUserDTO(user.getUsername(), token, user.getRole());
	}

}

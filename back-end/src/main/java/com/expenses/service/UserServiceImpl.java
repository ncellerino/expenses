package com.expenses.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.expenses.dto.LoggedUserDTO;
import com.expenses.dto.UserDTO;
import com.expenses.dto.UserToSaveDTO;
import com.expenses.exception.ErrorResponseEnum;
import com.expenses.exception.ServiceExceptionFactory;
import com.expenses.model.User;
import com.expenses.repository.UserRepository;
import com.expenses.security.JwtTokenHandler;
import com.expenses.security.PasswordEncoder;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenHandler jwtTokenHandler;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.repository = userRepository;
	}

	@Override
	public LoggedUserDTO saveUser(UserToSaveDTO userDTO) {
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
		// generate JWT token
		String token = jwtTokenHandler.generateToken(user);
		return toLoggedUserDTO(user, token);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO) {
		User userToUpdate = repository.findByUsername(userDTO.getUsername());
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
				String token = jwtTokenHandler.generateToken(user);
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
	public UserDTO getUser(String username) {
		UserDTO userDTO = null;
		User user = repository.findByUsername(username);
		if (user != null) {
			userDTO = toDTO(user);
		}
		return userDTO;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setJwtTokenHandler(JwtTokenHandler jwtTokenHandler) {
		this.jwtTokenHandler = jwtTokenHandler;
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}

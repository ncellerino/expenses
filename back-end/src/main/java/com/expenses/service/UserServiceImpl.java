package com.expenses.service;

import com.expenses.dto.LoggedUserDTO;
import com.expenses.dto.UserDTO;
import com.expenses.dto.UserToSaveDTO;
import com.expenses.exception.ErrorResponseEnum;
import com.expenses.exception.ServiceExceptionFactory;
import com.expenses.model.User;
import com.expenses.repository.UserRepository;
import com.expenses.security.JwtTokenHandler;
import com.expenses.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

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
    public void updateUser(UserDTO userDTO) {
        repository.findByUsername(userDTO.getUsername()).map(user -> saveUser(user, userDTO));
    }

    private UserDTO saveUser(User user, UserDTO userDTO) {
        User userModel = new User.UserBuilder(user.getId(), userDTO.getMail(), userDTO.getUsername(), userDTO.getRole(),
                userDTO.getFirstName(), userDTO.getLastName(), user.getPasswordHash(), user.getPasswordSalt())
                        .age(userDTO.getAge()).address(userDTO.getAddress()).phone(userDTO.getPhone()).build();

        user = repository.save(userModel);
        return toDTO(user);
    }

    @Override
    public Optional<LoggedUserDTO> getUserByUsernameAndPassword(String username, String password) {
       return repository.findByUsername(username).map(user -> {
            LoggedUserDTO loggedUserDTO = null;
            boolean userValidated = false;
			// check password
			try {
				userValidated = passwordEncoder.authenticate(password, user.getPasswordHash(),
						user.getPasswordSalt());
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				userValidated = false;
			}
			if (userValidated) {
				// generate JWT token
				String token = jwtTokenHandler.generateToken(user);
				loggedUserDTO = toLoggedUserDTO(user, token);
			}
            return Optional.ofNullable(loggedUserDTO);
        }).orElse(Optional.empty());
    }

    @Override
    public Optional<UserDTO> getUserByMail(String mail) {
        return repository.findByMail(mail).map(user -> Optional.of(toDTO(user))).orElse(Optional.empty());
    }

    @Override
    public Optional<UserDTO> getUser(String username) {
        return repository.findByUsername(username).map(user -> Optional.of(toDTO(user))).orElse(Optional.empty());
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setJwtTokenHandler(JwtTokenHandler jwtTokenHandler) {
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @Override
    public void deleteUser(String username) {
        repository.findByUsername(username).ifPresent(u -> {
            repository.delete(u);
        });
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

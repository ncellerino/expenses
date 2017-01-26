package com.expenses.repository;

import org.springframework.data.repository.CrudRepository;

import com.expenses.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
	
	Optional<User> findByMail(String mail);
	
	Optional<User> findByUsername(String username);

}

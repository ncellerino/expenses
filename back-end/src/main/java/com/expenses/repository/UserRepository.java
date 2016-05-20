package com.expenses.repository;

import org.springframework.data.repository.CrudRepository;

import com.expenses.model.User;

public interface UserRepository extends CrudRepository<User, String> {
	
	User findByMail(String mail);
	
	User findByUsername(String username);

}

package com.smartbusiness.repository;

import org.springframework.data.repository.CrudRepository;

import com.smartbusiness.model.User;

public interface UserRepository extends CrudRepository<User, String> {
	
	User findByMail(String mail);

}

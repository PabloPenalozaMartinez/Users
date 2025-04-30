package com.desafio.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.desafio.user.entity.User;
import com.desafio.user.exception.ValidationDataException;

public interface UserService {
	List<User> findAll();
	User saveUser(User user) throws ValidationDataException;
	User updateUser(User user);
	User updatePhones(User user);
	boolean validateUserByName(String name);
	Optional<User> findByUUID(UUID uuid);
}

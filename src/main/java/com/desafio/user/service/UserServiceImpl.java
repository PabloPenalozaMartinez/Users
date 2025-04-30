package com.desafio.user.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.desafio.user.entity.User;
import com.desafio.user.exception.ValidationDataException;
import com.desafio.user.repository.UserRepository;
import com.desafio.user.security.JWTUtil;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private JWTUtil jWTUtil;
	
	@Value("${regex.mail}")
    private String regexMail;
	
	@Value("${regex.password}")
	private String regexPassword;
	
	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User saveUser(User user) throws ValidationDataException {
		 
		this.validateUser(user);
		this.validateConsistancy(user);
		 
		user.setCreationDate(new Date());
		user.setModificationDate(new Date());
		user.setLastLoginDate(new Date());
		user.setToken(jWTUtil.generateToken(user.getName()));
		user.setActive(true);
		 
		return userRepository.save(user);
	}
	
	@Override
	public User updateUser(User user) throws ValidationDataException {
		this.validateConsistancy(user);
		user.setModificationDate(new Date());
		return userRepository.save(user);
	}
	
	@Override
	public User updatePhones(User user) {
		user.setModificationDate(new Date());
		return userRepository.save(user);
	}

	private void validateUser(User user) {
		List<User> lista = userRepository.findByEmail(user.getEmail());
		 
		if(lista.size() > 0)
			throw new ValidationDataException("El correo ya registrado");
	}
	
	private void validateConsistancy(User user) {
		
		if(!Pattern.compile(regexMail, Pattern.CASE_INSENSITIVE).matcher(user.getEmail()).matches())
			throw new ValidationDataException("El formato del correo no es correcto");
		
		if(!Pattern.compile(regexPassword, Pattern.CASE_INSENSITIVE).matcher(user.getPassword()).matches())
			throw new ValidationDataException("El formato de la clave no es correcto");
		
	}
	
	@Override
	public boolean validateUserByName(String name) {
		List<User> lista = userRepository.findByName(name);
		return lista.size() >= 1 ? true : false;
	}

	@Override
	public Optional<User> findByUUID(UUID uuid) {
		return userRepository.findById(uuid);
	}

}

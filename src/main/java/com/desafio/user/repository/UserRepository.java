package com.desafio.user.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafio.user.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
	
	@Query(value = "SELECT * FROM User WHERE name LIKE concat('%', :name,'%')", nativeQuery = true)
    List<User> findByName( @Param("name") String name);
	
	@Query(value = "SELECT * FROM User WHERE email LIKE concat('%', :email,'%')", nativeQuery = true)
	List<User> findByEmail( @Param("email") String email);
	
}

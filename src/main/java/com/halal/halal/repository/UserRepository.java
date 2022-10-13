package com.halal.halal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.halal.halal.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);

	Page<User> findAll(Pageable pageable);
	boolean existsByUsername(String username);

	


	
	

}

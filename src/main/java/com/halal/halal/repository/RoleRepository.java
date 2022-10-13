package com.halal.halal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.halal.halal.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}

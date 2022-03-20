package com.management.system.authservice.repository;

import com.management.system.authservice.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query(value = "SELECT * FROM role as r WHERE r.name = ?1 LIMIT 1", nativeQuery = true)
    Optional<Role> getByName(String name);
}

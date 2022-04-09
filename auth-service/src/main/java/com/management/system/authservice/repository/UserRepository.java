package com.management.system.authservice.repository;

import com.management.system.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUsername(String username);

    boolean existsByUsername(String username);

    @Query(value = "SELECT * FROM users AS u WHERE u.uuid = ?1 LIMIT 1", nativeQuery = true)
    Optional<User> findByUuid(UUID uuid);

}

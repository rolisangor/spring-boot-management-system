package com.management.system.authservice.repository;

import com.management.system.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    @Query(value = "SELECT * FROM users as u WHERE u.username= ?1 LIMIT 1", nativeQuery = true)
//    Optional<User> findByUsername(String username);

    Optional<User> findFirstByUsername(String username);

//    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = ?1")
//    boolean existByUsername(String username);

    boolean existsByUsername(String username);
}

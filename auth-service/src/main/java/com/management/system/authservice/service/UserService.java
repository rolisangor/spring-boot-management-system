package com.management.system.authservice.service;

import com.management.system.authservice.model.User;
import com.management.system.authservice.model.dto.PasswordUpdateDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> save(User user);
    void deleteById(Long id);
    void deleteByUser(User user);
    Optional<User> getById(Long id);
    Optional<User> getByUsername(String username);
    boolean existById(Long id);
    boolean existByUsername(String username);
    Optional<User> update(User user);
    Iterable<User> getAll();
    Optional<User> updatePassword(PasswordUpdateDto passwordUpdateDto, String username);
}
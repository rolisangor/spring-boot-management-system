package com.management.system.authservice.service;

import com.management.system.authservice.model.Role;
import com.management.system.authservice.model.User;

import java.util.Optional;

public interface RoleService {

    Optional<Role> save(Role role);
    void deleteById(Long id);
    void deleteByRole(Role role);
    Optional<Role> getById(Long id);
    Optional<Role> getByName(String name);
    boolean existById(Long id);
    boolean existByName(String name);
    Optional<Role> update(Role role);
}

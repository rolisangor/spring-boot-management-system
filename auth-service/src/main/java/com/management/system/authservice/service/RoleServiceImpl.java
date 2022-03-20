package com.management.system.authservice.service;

import com.management.system.authservice.model.Role;
import com.management.system.authservice.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> save(Role role) {
        return Optional.of(roleRepository.save(role));
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByRole(Role role) {

    }

    @Override
    public Optional<Role> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Role> getByName(String name) {
        return roleRepository.getByName(name);
    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    @Override
    public boolean existByName(String name) {
        return false;
    }

    @Override
    public Optional<Role> update(Role role) {
        return Optional.empty();
    }
}

package com.management.system.authservice.service;

import com.management.system.authservice.exception.PasswordValidationException;
import com.management.system.authservice.exception.RoleNotFoundException;
import com.management.system.authservice.exception.UserNotFoundException;
import com.management.system.authservice.model.User;
import com.management.system.authservice.model.dto.PasswordUpdateDto;
import com.management.system.authservice.repository.RoleRepository;
import com.management.system.authservice.repository.UserRepository;
import com.management.system.authservice.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;


    @Transactional
    @Override
    public Optional<User> save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(roleRepository.getByName("USER").orElseThrow(() -> new RoleNotFoundException("Role not found")));
        }
        log.info("SERVICE_SAVE_USER: call another microservice");
        return Optional.of(userRepository.save(user));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByUser(User user) {
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existById(Long id) {
        return userRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    @Override
    public Optional<User> update(User user) {
        User current = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        current.setRole(roleRepository.getByName(user.getRole().getName())
                .orElseThrow(() -> new RoleNotFoundException("Role not found")));
        current.setUsername(user.getUsername());
        return Optional.of(userRepository.save(current));
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<User> updatePassword(PasswordUpdateDto passwordUpdateDto, String username) {
        User user = getByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!passwordEncoder.matches(passwordUpdateDto.getCurrentPassword(), user.getPassword())) {
            throw new PasswordValidationException("Old password is not correct");
        }
        if (!passwordUpdateDto.getNewPassword().equals(passwordUpdateDto.getRepeatNewPassword())) {
            throw new PasswordValidationException("New password and repeat password must be equals");
        }
        user.setPassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
        return Optional.of(userRepository.save(user));
    }
}

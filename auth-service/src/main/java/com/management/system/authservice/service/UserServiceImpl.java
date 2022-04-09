package com.management.system.authservice.service;

import com.management.system.authservice.controller.advice.ResponseError;
import com.management.system.authservice.exception.*;
import com.management.system.authservice.model.User;
import com.management.system.authservice.model.dto.*;
import com.management.system.authservice.repository.RoleRepository;
import com.management.system.authservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final WebClient.Builder webClient;
    private final static String PROFILE_SERVICE_BASE_URL = "http://profile-service/";
    private final static String PROFILE_SERVICE_CREATE_URL = "/api/profile/";
    private final static String PROFILE_SERVICE_DELETE_BY_UUID_URL = "/api/profile/%s";

    @Transactional
    @Override
    public User save(RegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new UserRegistrationException(String.format("User with email %s already exist", registrationDto.getUsername()));
        }
        UUID uuid = UUID.randomUUID();
        User user = User.builder()
                .uuid(uuid)
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .username(registrationDto.getUsername())
                .role(roleRepository.getByName("USER").orElseThrow(() -> new RoleNotFoundException("Role not found")))
                .build();

        ProfileDto profile = ProfileDto.builder()
                .uuid(user.getUuid())
                .fullName(registrationDto.getFullName())
                .email(registrationDto.getUsername())
                .build();

        webClient
                .baseUrl(PROFILE_SERVICE_BASE_URL).build()
                .post()
                .uri(PROFILE_SERVICE_CREATE_URL)
                .header(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(profile), ProfileDto.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> response
                        .bodyToMono(ResponseError.class)
                        .flatMap(error -> Mono.error(new BadRequestException(error.getMessage()))))
                .onStatus(HttpStatus::is5xxServerError, error ->
                        Mono.error(new InternalServiceException("Profile service request failed")))
                .bodyToMono(ProfileDto.class)
                .block();
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteByUuid(UUID uuid) {
        User user = userRepository.findByUuid(uuid).orElseThrow(() ->
                new UserNotFoundException("Delete user failed, user not found"));
        webClient
                .baseUrl(PROFILE_SERVICE_BASE_URL).build()
                .delete()
                .uri(String.format(PROFILE_SERVICE_DELETE_BY_UUID_URL, uuid))
                .header(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> response
                        .bodyToMono(ResponseError.class)
                        .flatMap(error -> Mono.error(new BadRequestException(error.getMessage()))))
                .onStatus(HttpStatus::is5xxServerError, error ->
                        Mono.error(new InternalServiceException("Profile service request failed")))
                .bodyToMono(ResponseMessageDto.class)
                .block();
        userRepository.deleteById(user.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    @Transactional
    @Override
    public Optional<User> update(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new BadRequestException(String.format("Email %s already exist", userDto.getUsername()));
        }
        User user = userRepository.findByUuid(userDto.getUuid()).orElseThrow(() ->
                new UserNotFoundException("User update failed, user not found"));
        return Optional.of(userRepository.save(user));
    }

    @Transactional
    @Override
    public Optional<User> updatePassword(PasswordUpdateDto passwordUpdateDto) {
        User user = userRepository.findByUuid(passwordUpdateDto.getUuid()).orElseThrow(() ->
                new UserNotFoundException("User not found"));
        if (!passwordEncoder.matches(passwordUpdateDto.getOldPassword(), user.getPassword())) {
            throw new PasswordValidationException("Old password is not correct");
        }
        if (!passwordUpdateDto.getNewPassword().equals(passwordUpdateDto.getRepeatPassword())) {
            throw new PasswordValidationException("New password and repeat password must be the same");
        }
        user.setPassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
        return Optional.of(userRepository.save(user));
    }
}

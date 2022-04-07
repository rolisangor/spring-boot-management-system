package com.management.system.authservice.service;

import com.management.system.authservice.exception.*;
import com.management.system.authservice.model.User;
import com.management.system.authservice.model.dto.PasswordUpdateDto;
import com.management.system.authservice.model.dto.ProfileDto;
import com.management.system.authservice.model.dto.RegistrationDto;
import com.management.system.authservice.model.dto.ResponseMessageDto;
import com.management.system.authservice.repository.RoleRepository;
import com.management.system.authservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
    private final static String PROFILE_SERVICE_DELETE_URL = "/api/profile/%s/";

    @Transactional
    @Override
    public User save(RegistrationDto registrationDto) {
        User user = User.builder()
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .username(registrationDto.getUsername())
                .role(roleRepository.getByName("USER").orElseThrow(() -> new RoleNotFoundException("Role not found")))
                .build();

        User currentUser = userRepository.save(user);
        log.info("AUTH_SERVICE_SAVE: {}", user);
        ProfileDto profile = ProfileDto.builder()
                .id(user.getId())
                .email(user.getUsername())
                .fullName(registrationDto.getFullName())
                .avatarUrl("https://robohash.org/omnisdoloribusquos.png?size=50x50&set=set1")
                .build();

        webClient
                .baseUrl(PROFILE_SERVICE_BASE_URL).build()
                .post()
                .uri(PROFILE_SERVICE_CREATE_URL)
                .header(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(profile), ProfileDto.class)
                .retrieve()
                .bodyToMono(ProfileDto.class)
                .onErrorMap(throwable -> new InternalServiceException("Profile service request failed"))
                .block();

        return currentUser;
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

    @Transactional
    @Override
    public Optional<User> updatePassword(PasswordUpdateDto passwordUpdateDto) {
        User user = getByUsername(passwordUpdateDto.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!passwordEncoder.matches(passwordUpdateDto.getOldPassword(), user.getPassword())) {
            throw new PasswordValidationException("Old password is not correct");
        }
        if (!passwordUpdateDto.getNewPassword().equals(passwordUpdateDto.getRepeatPassword())) {
            throw new PasswordValidationException("New password and repeat password must be the same");
        }
        user.setPassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
        return Optional.of(userRepository.save(user));
    }

    @Transactional
    @Override
    public void deleteUserByEmail(String email) {
        webClient
                .baseUrl(PROFILE_SERVICE_BASE_URL).build()
                .delete()
                .uri(String.format(PROFILE_SERVICE_DELETE_URL, email))
                .header(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, error ->
//                        Mono.error(new BadRequestException("Profile response failed")))
//                .onStatus(HttpStatus::is5xxServerError, error ->
//                        Mono.error(new InternalServiceException("Profile service request failed")))
                .bodyToMono(ResponseMessageDto.class)
                .onErrorMap(throwable -> new InternalServiceException("Profile service request failed"))
                .block();
        userRepository.findFirstByUsername(email).ifPresent(user -> userRepository.deleteById(user.getId()));
    }
}

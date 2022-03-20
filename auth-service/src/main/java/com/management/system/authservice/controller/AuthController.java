package com.management.system.authservice.controller;

import com.management.system.authservice.exception.PasswordValidationException;
import com.management.system.authservice.exception.UserRegistrationException;
import com.management.system.authservice.model.User;
import com.management.system.authservice.model.dto.*;
import com.management.system.authservice.service.UserService;
import com.management.system.authservice.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    public ResponseEntity<UserPrincipalDto> registration(@RequestBody RegistrationDto registrationDto) {
        log.info("REGISTRATION: save user email: {} and password {}", registrationDto.getUsername(), registrationDto.getPassword());
        User user = userService.save(userMapper.toUser(registrationDto)).orElseThrow(() -> new UserRegistrationException("Registration failed please contact support operator"));
        return ResponseEntity.ok(userMapper.toPrincipal(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Principal principal) {
        log.info("LOGOUT: user logout with email: {}", principal.getName());
        return ResponseEntity.ok("logout success");
    }

    @GetMapping("/principal")
    public ResponseEntity<?> principal(Principal principal) {
        log.info("PRINCIPAL: user principal with email: {}", principal.getName());
        User user = userService.getByUsername(principal.getName()).orElseThrow();
        return ResponseEntity.ok(userMapper.toPrincipal(user));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDto userDto) {
//        log.info("PRINCIPAL: user principal with email: {}", principal.getName());
        log.info("USER_DTO_ROLE: {}", userDto.getRole());

        Optional<User> user = userService.update(userMapper.toUser(userDto));
        return ResponseEntity.ok(userMapper.toUserDto(user.orElseThrow()));
    }

    @PutMapping("/password-update")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateDto passwordUpdateDto, Principal principal) {
        User user = userService.updatePassword(passwordUpdateDto, principal.getName())
                        .orElseThrow(() -> new PasswordValidationException("Update password error please contact support operator"));
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody SaveUserByAdmin saveUserByAdmin) {
        userService.save(userMapper.toUser(saveUserByAdmin))
                .orElseThrow(() -> new UserRegistrationException("User saved failed contact please support operator"));
        return ResponseEntity.ok("user has been saved");
    }
}

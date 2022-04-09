package com.management.system.authservice.controller;

import com.management.system.authservice.exception.InternalServiceException;
import com.management.system.authservice.exception.UserNotFoundException;
import com.management.system.authservice.model.User;
import com.management.system.authservice.model.dto.*;
import com.management.system.authservice.service.UserService;
import com.management.system.authservice.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

import static com.management.system.authservice.controller.ResponseMessage.message;

@RestController
@RequestMapping("api/auth/")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    public ResponseEntity<UserPrincipalDto> registration(@Valid @RequestBody RegistrationDto registrationDto) {
        log.info("CONTROLLER_REGISTRATION_USER_EMAIL: {}", registrationDto.getUsername());
        User user = userService.save(registrationDto);
        return ResponseEntity.ok(userMapper.toPrincipal(user));
    }

    @GetMapping("/principal")
    public ResponseEntity<?> principal(Principal principal) {
        User user = userService.getByUsername(principal.getName()).orElseThrow(() ->
                new UserNotFoundException("User not found"));
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDto userDto) {
        User user = userService.update(userDto).orElseThrow(() ->
                new InternalServiceException("User update failed please contact support operator"));
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @PutMapping("/password-update")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateDto passwordUpdateDto) {
        User user = userService.updatePassword(passwordUpdateDto).orElseThrow(() ->
                new InternalServiceException("User update password failed please contact support operator"));
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @DeleteMapping("/id/{uuid}")
    public ResponseEntity<?> deleteUserByUUID(@PathVariable UUID uuid) {
        log.info("CONTROLLER_DELETE_UUID: {}", uuid);
        userService.deleteByUuid(uuid);
        return ResponseEntity.ok(message("User deleted successful"));
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<?> getUserByUUID(@PathVariable UUID uuid) {
        User user = userService.findByUuid(uuid).orElseThrow(() -> new UserNotFoundException("User not found"));
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

}

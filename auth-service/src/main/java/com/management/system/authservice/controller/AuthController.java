package com.management.system.authservice.controller;

import com.management.system.authservice.exception.PasswordValidationException;
import com.management.system.authservice.exception.UserNotFoundException;
import com.management.system.authservice.exception.UserRegistrationException;
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
import java.util.Optional;
import static com.management.system.authservice.controller.ResponseMessage.message;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    public ResponseEntity<UserPrincipalDto> registration(@Valid @RequestBody RegistrationDto registrationDto) {
        log.info("REGISTRATION: save user email: {} and password {}",
                registrationDto.getUsername(),
                registrationDto.getPassword());
        User user = userService.save(registrationDto);
        return ResponseEntity.ok(userMapper.toPrincipal(user));
    }

    @GetMapping("/principal")
    public ResponseEntity<?> principal(Principal principal) {
        User user = userService.getByUsername(principal.getName()).orElseThrow();
        return ResponseEntity.ok(userMapper.toPrincipal(user));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDto userDto) {
        Optional<User> user = userService.update(userMapper.toUser(userDto));
        return ResponseEntity.ok(userMapper.toUserDto(user.orElseThrow()));
    }

    @PutMapping("/password-update")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateDto passwordUpdateDto) {
        log.info(passwordUpdateDto.toString());
        User user = userService.updatePassword(passwordUpdateDto).orElseThrow(() ->
                new PasswordValidationException("Update password error please contact support operator"));
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @DeleteMapping("/{email}/")
    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok(message("User deleted successful"));
    }

    @GetMapping("/email/{email}/")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        User user = userService.getByUsername(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

}

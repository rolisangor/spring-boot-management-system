package com.management.system.authservice.application;

import com.management.system.authservice.model.Role;
import com.management.system.authservice.model.User;
import com.management.system.authservice.model.dto.RegistrationDto;
import com.management.system.authservice.repository.UserRepository;
import com.management.system.authservice.service.RoleService;
import com.management.system.authservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InitData implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

//        Role userRole = Role.builder().name("USER").build();
//        Role adminRole = Role.builder().name("ADMIN").build();
//
//        roleService.save(userRole);
//        roleService.save(adminRole);
//
//        User user = User.builder()
//                .username("user")
//                .password(passwordEncoder.encode("pass"))
//                .role(roleService.getByName("USER").orElseThrow())
//                .build();
//        User admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("pass"))
//                .role(roleService.getByName("ADMIN").orElseThrow())
//                .build();
//
//        userRepository.save(user);
//        userRepository.save(admin);
    }
}

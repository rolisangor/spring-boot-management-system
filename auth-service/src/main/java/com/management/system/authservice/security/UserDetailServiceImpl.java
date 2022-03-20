package com.management.system.authservice.security;

import com.management.system.authservice.exception.UserNotFoundException;
import com.management.system.authservice.model.User;
import com.management.system.authservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("USER_DETAILS_USERNAME: {}", username);
        User user = userService
                .getByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("Customer with email: %s NotFound", username)));
        log.info("USER_PRINCIPAL_ROLE: {}", user.getRole().getName());
        List<GrantedAuthority> grantedAuthority = List.of(new SimpleGrantedAuthority(user.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthority);
    }
}

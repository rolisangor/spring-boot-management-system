package com.management.system.authservice.service;

import com.management.system.authservice.model.User;
import com.management.system.authservice.model.dto.PasswordUpdateDto;
import com.management.system.authservice.model.dto.RegistrationDto;
import com.management.system.authservice.model.dto.UserDto;

import javax.sql.rowset.serial.SerialStruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User save(RegistrationDto registrationDto);
    void deleteByUuid(UUID uuid);
    Optional<User> findByUuid(UUID uuid);
    Optional<User> getByUsername(String username);
    Optional<User> update(UserDto userDto);
    Optional<User> updatePassword(PasswordUpdateDto passwordUpdateDto);
}

package com.management.system.authservice.service.mapper;

import com.management.system.authservice.model.User;
import com.management.system.authservice.model.dto.RegistrationDto;
import com.management.system.authservice.model.dto.UserDto;
import com.management.system.authservice.model.dto.UserPrincipalDto;
import com.management.system.authservice.service.RoleService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.management.system.authservice.exception.RoleNotFoundException;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected RoleService roleService;
    protected RoleNotFoundException roleNotFoundException = new RoleNotFoundException("Role not found");

    @Mapping(target = "role", source = "role.name")
    public abstract UserPrincipalDto toPrincipal(User user);

    @Mapping(target = "role", expression = "java(roleService.getByName(userDto.getRole()).orElseThrow(() -> roleNotFoundException))")
    public abstract User toUser(UserDto userDto);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract User toUser(RegistrationDto registrationDto);

    @Mapping(target = "role", source = "role.name")
    public abstract UserDto toUserDto(User user);

}

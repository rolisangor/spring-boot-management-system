package com.management.system.authservice.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    @NotNull(message = "uuid must not be null")
    @NotEmpty(message = "uuid must not be empty")
    @NotBlank(message = "uuid must not be blank")
    private UUID uuid;

    @NotEmpty(message = "username must not be empty")
    @NotBlank(message = "username must not be blank")
    private String username;

    @NotEmpty(message = "role must not be empty")
    @NotBlank(message = "role must not be blank")
    private String role;
}

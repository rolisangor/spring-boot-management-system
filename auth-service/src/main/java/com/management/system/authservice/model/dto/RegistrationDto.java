package com.management.system.authservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.management.system.authservice.validation.PasswordMatch;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@PasswordMatch(first = "password", second = "repeatPassword")
public class RegistrationDto {

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String fullName;

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String username;

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String password;

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String repeatPassword;
}

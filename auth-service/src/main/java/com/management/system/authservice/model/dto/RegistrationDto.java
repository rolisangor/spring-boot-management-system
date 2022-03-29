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
    @NotEmpty(message = "Full name must not be empty")
    @NotBlank(message = "Full name must not be blank")
    private String fullName;

    @JsonProperty(required = true)
    @NotEmpty(message = "Email must not be empty")
    @NotBlank(message = "Email must not be blank")
    private String username;

    @JsonProperty(required = true)
    @NotEmpty(message = "Password must not be empty")
    @NotBlank(message = "Password must not be blank")
    private String password;

    @JsonProperty(required = true)
    @NotEmpty(message = "Repeat password must not be empty")
    @NotBlank(message = "Repeat password must not be blank")
    private String repeatPassword;
}

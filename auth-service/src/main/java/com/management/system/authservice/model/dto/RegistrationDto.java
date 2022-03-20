package com.management.system.authservice.model.dto;

import com.management.system.authservice.validation.PasswordMatch;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@PasswordMatch(first = "password", second = "repeatPassword")
public class RegistrationDto {

    private String username;
    private String password;
    private String repeatPassword;
}

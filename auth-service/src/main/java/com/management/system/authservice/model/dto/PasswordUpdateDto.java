package com.management.system.authservice.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PasswordUpdateDto {

    private String currentPassword;
    private String newPassword;
    private String repeatNewPassword;
}

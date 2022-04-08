package com.management.system.authservice.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PasswordUpdateDto {

    private String username; //TODO change to id
    private String oldPassword;
    private String newPassword;
    private String repeatPassword;
}

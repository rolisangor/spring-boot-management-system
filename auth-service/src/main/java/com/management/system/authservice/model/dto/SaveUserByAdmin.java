package com.management.system.authservice.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SaveUserByAdmin {

    private String username;
    private String password;
    private String role;
}

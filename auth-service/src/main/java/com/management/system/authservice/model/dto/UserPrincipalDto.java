package com.management.system.authservice.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPrincipalDto { //TODO delete after refactor controller and ui

    private Long id;
    private String username;
    private String role;
}

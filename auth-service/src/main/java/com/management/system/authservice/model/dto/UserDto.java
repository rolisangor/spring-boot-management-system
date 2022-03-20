package com.management.system.authservice.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private Long id;
    private String username;
    private String role;
}

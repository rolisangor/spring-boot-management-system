package com.management.system.authservice.model.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    private UUID uuid;
    private String fullName;
    private String email;
}

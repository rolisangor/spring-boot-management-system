package com.management.system.authservice.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    private Long id;
    private String email;
    private String fullName;
    private String description;
    private String position;
    private String avatarUrl;
    private String country;
    private String city;
    private String address;
}

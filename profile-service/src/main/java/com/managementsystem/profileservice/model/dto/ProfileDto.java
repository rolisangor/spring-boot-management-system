package com.managementsystem.profileservice.model.dto;

import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    private Long id;
    private String email; //TODO delete email from profile
    private String fullName;
    private String description;
    private String position;
    private String avatarUrl;
    protected Instant createdAt;
    private String country;
    private String city;
    private String address;
    private String about;
}

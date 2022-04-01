package com.managementsystem.profileservice.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePrincipalDto {

    private String fullName;
    private String position;
    private String avatarUrl;
}

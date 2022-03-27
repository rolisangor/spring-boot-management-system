package com.managementsystem.profileservice.model.dto;

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
    //    private String persistDate;
    private String description;
    //    private String position;
    private String avatarUrl;
}

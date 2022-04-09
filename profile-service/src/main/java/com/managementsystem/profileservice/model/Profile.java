package com.managementsystem.profileservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "profile")
public class Profile extends BaseEntity{

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String description;
    private String position;
    private String avatarUrl;
    private String country;
    private String city;
    private String address;
    private String about;
}

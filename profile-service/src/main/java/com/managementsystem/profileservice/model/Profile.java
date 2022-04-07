package com.managementsystem.profileservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "profile")
public class Profile extends BaseEntity{

    @Id
    protected Long id;
    private String email;
    private String fullName;
    private String description;
    private String position;
    private String avatarUrl;
    private String country;
    private String city;
    private String address;
    private String about;
}

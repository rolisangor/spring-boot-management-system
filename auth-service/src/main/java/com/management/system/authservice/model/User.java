package com.management.system.authservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String username; //TODO: add unique field

    private String password;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role_id", nullable = false)
    @ToString.Exclude
    private Role role;
}

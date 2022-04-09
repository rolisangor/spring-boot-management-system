package com.management.system.authservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Table(name = "users")
public class User extends BaseEntity{

    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role_id", nullable = false)
    @ToString.Exclude
    private Role role;
}

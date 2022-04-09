package com.managementsystem.profileservice.repository;

import com.managementsystem.profileservice.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUuid(UUID uuid);
    boolean existsByUuid(UUID uuid);
}

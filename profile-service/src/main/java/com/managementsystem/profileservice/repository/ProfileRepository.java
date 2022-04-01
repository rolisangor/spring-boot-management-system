package com.managementsystem.profileservice.repository;

import com.managementsystem.profileservice.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> getProfileByEmail(String email);
}

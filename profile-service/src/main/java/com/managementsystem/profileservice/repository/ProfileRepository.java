package com.managementsystem.profileservice.repository;

import com.managementsystem.profileservice.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}

package com.managementsystem.profileservice.service;

import com.managementsystem.profileservice.model.Profile;
import com.managementsystem.profileservice.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;

    @Transactional
    @Override
    public Optional<Profile> save(Profile profile) {
        return Optional.of(profileRepository.save(profile));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Profile> getProfileById(Long id) {
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Profile> getProfileByEmail(String email) {
        log.info("PROFILE_SERVICE_GET_BY_EMAIL: {}", email);
        return profileRepository.getProfileByEmail(email);
    }
}

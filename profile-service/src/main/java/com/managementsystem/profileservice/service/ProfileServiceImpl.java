package com.managementsystem.profileservice.service;

import com.managementsystem.profileservice.model.Profile;
import com.managementsystem.profileservice.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;

    @Override
    public Optional<Profile> save(Profile profile) {
        return Optional.of(profileRepository.save(profile));
    }

    @Override
    public Optional<Profile> getProfileById(Long id) {
        return Optional.empty();
    }
}

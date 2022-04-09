package com.managementsystem.profileservice.service;

import com.managementsystem.profileservice.model.Profile;
import com.managementsystem.profileservice.model.dto.ProfileDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileService {

    Optional<Profile> save(Profile profile);
    Optional<Profile> getProfileByUuid(UUID uuid);
    List<Profile> getAllProfiles();
    List<Profile> getPageableProfiles(String page, String size);
    void deleteProfileByUuid(UUID uuid);
    Optional<Profile> update(ProfileDto profileDto);
}

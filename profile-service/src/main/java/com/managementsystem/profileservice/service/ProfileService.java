package com.managementsystem.profileservice.service;

import com.managementsystem.profileservice.model.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    Optional<Profile> save(Profile profile);
    Optional<Profile> getProfileById(Long id);
    Optional<Profile> getProfileByEmail(String email);
    List<Profile> getAllProfiles();
    List<Profile> getPageableProfiles(String page, String size);
    void deleteProfileById(Long id);
}

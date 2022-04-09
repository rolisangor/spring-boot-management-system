package com.managementsystem.profileservice.service;

import com.managementsystem.profileservice.exception.BadRequestParamException;
import com.managementsystem.profileservice.exception.ProfileNotFoundException;
import com.managementsystem.profileservice.model.Profile;
import com.managementsystem.profileservice.model.dto.ProfileDto;
import com.managementsystem.profileservice.repository.ProfileRepository;
import com.managementsystem.profileservice.service.mapper.ProfileMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Transactional
    @Override
    public Optional<Profile> save(Profile profile) {
        log.info("PROFILE_SERVICE_SAVE: {}", profile);
        profile.setAvatarUrl("https://robohash.org/omnisdoloribusquos.png?size=150x150&set=set1");
        return Optional.of(profileRepository.save(profile));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Profile> getProfileByUuid(UUID uuid) {
        return profileRepository.findByUuid(uuid);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Profile> getPageableProfiles(String page, String size) {
        int currentPage;
        int currentSize;
        try {
            currentPage = Optional.of(Integer.parseInt(page)).get();
            currentSize = Optional.of(Integer.parseInt(size)).get();
        }catch (Exception e) {
            throw new BadRequestParamException("page and size must be a number");
        }
        if (currentPage < 1 || currentSize < 1) {
            throw new BadRequestParamException("page and size must not be less than one");
        }
        Pageable pageable = PageRequest.of(currentPage - 1, currentSize);
        return profileRepository.findAll(pageable).getContent();
    }

    @Transactional
    @Override
    public void deleteProfileByUuid(UUID uuid) {
        Profile profile = profileRepository.findByUuid(uuid).orElseThrow(() ->
                new ProfileNotFoundException("Delete profile failed profile not found"));
        profileRepository.deleteById(profile.getId());
    }

    @Transactional
    @Override
    public Optional<Profile> update(ProfileDto profileDto) {
        Profile profile = profileRepository.findByUuid(profileDto.getUuid()).orElseThrow(() ->
                new ProfileNotFoundException("Profile update failed, profile not found"));
        Profile updatableProfile = profileMapper.toProfile(profileDto);
        updatableProfile.setId(profile.getId());
        updatableProfile.setVersion(profile.getVersion());
        return Optional.of(profileRepository.saveAndFlush(updatableProfile));
    }

}

package com.managementsystem.profileservice.service;

import com.managementsystem.profileservice.exception.BadRequestParamException;
import com.managementsystem.profileservice.model.Profile;
import com.managementsystem.profileservice.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        return profileRepository.getProfileByEmail(email);
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

}

package com.managementsystem.profileservice.controller;

import com.managementsystem.profileservice.exception.CreateProfileError;
import com.managementsystem.profileservice.model.Profile;
import com.managementsystem.profileservice.model.dto.ProfileDto;
import com.managementsystem.profileservice.service.ProfileService;
import com.managementsystem.profileservice.service.mapper.ProfileMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile/")
@Slf4j
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('server')")
    public ResponseEntity<?> create(@RequestBody ProfileDto profileDto) {
        log.info("PROFILE_CONTROLLER_CREATE_DATA: {}", profileDto.getFullName());
        Profile profile = profileService.save(
                profileMapper.toProfile(profileDto)).orElseThrow(() -> new CreateProfileError("Error create profile"));
        return ResponseEntity.ok(profileMapper.toProfileDto(profile));
    }

}

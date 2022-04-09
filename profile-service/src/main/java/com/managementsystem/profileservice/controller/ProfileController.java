package com.managementsystem.profileservice.controller;

import com.managementsystem.profileservice.controller.response.ResponseMessage;
import com.managementsystem.profileservice.exception.BadRequestParamException;
import com.managementsystem.profileservice.exception.CreateProfileError;
import com.managementsystem.profileservice.exception.ProfileNotFoundException;
import com.managementsystem.profileservice.model.Profile;
import com.managementsystem.profileservice.model.dto.ProfileDto;
import com.managementsystem.profileservice.service.ProfileService;
import com.managementsystem.profileservice.service.mapper.ProfileMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.managementsystem.profileservice.controller.response.ResponseMessage.message;

@RestController
@RequestMapping("/api/profile/")
@Slf4j
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('server')")
    public ResponseEntity<ProfileDto> create(@Valid @RequestBody ProfileDto profileDto) {
        log.info("PROFILE_CONTROLLER_CREATE_FULL_NAME: {}", profileDto.getFullName());
        Profile profile = profileService.save(
                profileMapper.toProfile(profileDto)).orElseThrow(() ->
                new CreateProfileError("Error create profile"));
        return ResponseEntity.ok(profileMapper.toProfileDto(profile));
    }

    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @GetMapping("/pageable")
    public ResponseEntity<List<Profile>> getPageableProfile(
            @RequestParam Optional<String> page, @RequestParam Optional<String> size) {
        if (page.isEmpty() || size.isEmpty()) {
            throw new BadRequestParamException("Request parameter page and size are required");
        }
        return ResponseEntity.ok(profileService.getPageableProfiles(page.get(), size.get()));
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @DeleteMapping("{uuid}")
    public ResponseEntity<ResponseMessage> deleteProfileByUuid(@PathVariable UUID uuid) {
        log.info("CONTROLLER_DELETE_UUID: {}", uuid);
        profileService.deleteProfileByUuid(uuid);
        return ResponseEntity.ok(message("Profile deleted successful"));
    }

    @GetMapping("/id/{uuid}")
    public ResponseEntity<ProfileDto> getProfileByUuid(@PathVariable UUID uuid) {
        Profile profile = profileService.getProfileByUuid(uuid).orElseThrow(() ->
                new ProfileNotFoundException("profile not found"));
        return ResponseEntity.ok(profileMapper.toProfileDto(profile));
    }

    @PutMapping
    public ResponseEntity<ProfileDto> updateProfile(@Valid @RequestBody ProfileDto profileDto) {
        Profile profile = profileService.update(profileDto).orElseThrow(() ->
                new CreateProfileError("Update profile failed"));
        return ResponseEntity.ok(profileMapper.toProfileDto(profile));
    }

}
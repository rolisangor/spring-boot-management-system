package com.managementsystem.profileservice.controller;

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

import java.security.Principal;
import java.util.Optional;

import static com.managementsystem.profileservice.controller.ResponseMessage.message;

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
        log.info("PROFILE_CONTROLLER_CREATE_FULL_NAME: {}", profileDto.getFullName());
        log.info("PROFILE_CONTROLLER_CREATE_ID: {}", profileDto.getId());
        Profile profile = profileService.save(
                profileMapper.toProfile(profileDto)).orElseThrow(() -> new CreateProfileError("Error create profile"));
        return ResponseEntity.ok(profileMapper.toProfileDto(profile));
    }

    @GetMapping()
    public ResponseEntity<?> getProfile(Principal principal) {
        log.info("PROFILE_CONTROLLER_GET_PROFILE_PRINCIPAL_NAME: {}", principal.getName());
        Profile profile = profileService.getProfileByEmail(principal.getName()).orElseThrow(() ->
                new ProfileNotFoundException("Profile not found please contact support operator"));
        return ResponseEntity.ok(profileMapper.toProfileDto(profile));
    }

    @GetMapping("/principal")
    public ResponseEntity<?> getProfilePrincipal(Principal principal) {
        log.info("PROFILE_CONTROLLER_GET_PROFILE_PRINCIPAL_NAME: {}", principal.getName());
        Profile profile = profileService.getProfileByEmail(principal.getName()).orElseThrow(() ->
                new ProfileNotFoundException("Profile not found please contact support operator"));
        return ResponseEntity.ok(profileMapper.toProfilePrincipalDto(profile));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @GetMapping("/pageable")
    public ResponseEntity<?> getPageableProfile(
            @RequestParam Optional<String> page, @RequestParam Optional<String> size) {
        if (page.isEmpty() || size.isEmpty()) {
            throw new BadRequestParamException("Request parameter page and size are required");
        }
        return ResponseEntity.ok(profileService.getPageableProfiles(page.get(), size.get()));
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteProfileById(@PathVariable Long id) {
        profileService.deleteProfileById(id);
        return ResponseEntity.ok(message("Profile deleted successful"));
    }

    @GetMapping("/id/{id}/")
    public ResponseEntity<?> getProfileById(@PathVariable Long id) {
        Profile profile = profileService.getProfileById(id).orElseThrow(() ->
                new ProfileNotFoundException("profile not found"));
        return ResponseEntity.ok(profileMapper.toProfileDto(profile));
    }

//    //TODO: delete this method after refactor UI
//    @PreAuthorize("#oauth2.hasScope('server')")
//    @DeleteMapping("/{email}/")
//    public ResponseEntity<?> deleteProfileByEmail(@PathVariable String email) {
//        profileService.deleteProfileByEmail(email);
//        return ResponseEntity.ok(message("Profile deleted successful"));
//    }
//
//    //TODO: delete this method after refactor UI
//    @GetMapping("/email/{email}/")
//    public ResponseEntity<?> getProfileByEmail(@PathVariable String email) {
//        Profile profile = profileService.getProfileByEmail(email).orElseThrow(() ->
//                new ProfileNotFoundException("profile not found"));
//        return ResponseEntity.ok(profileMapper.toProfileDto(profile));
//    }
//
//    //TODO: delete this method after refactor UI
//    @PutMapping("/email/{email}/")
//    public ResponseEntity<?> updateProfile(@PathVariable String email) {
//        //TODO: add service update class
//        return ResponseEntity.ok(message("updated successful")); //TODO: return profile class
//    }

}

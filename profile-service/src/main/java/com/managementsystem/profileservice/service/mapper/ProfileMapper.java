package com.managementsystem.profileservice.service.mapper;

import com.managementsystem.profileservice.model.Profile;
import com.managementsystem.profileservice.model.dto.ProfileDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    Profile toProfile(ProfileDto profileDto);
    ProfileDto toProfileDto(Profile profile);
}

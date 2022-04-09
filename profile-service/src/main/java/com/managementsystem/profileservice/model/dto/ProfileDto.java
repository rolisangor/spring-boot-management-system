package com.managementsystem.profileservice.model.dto;

import com.managementsystem.profileservice.service.validation.ValidUuid;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    @ValidUuid(message = "Require valid uuid")
    private java.util.UUID uuid;

    @NotEmpty(message = "Full Name must not be empty")
    @NotBlank(message = "Full Name must not be blank")
    private String fullName;

    @NotEmpty(message = "Email must not be empty")
    @NotBlank(message = "Email must not be blank")
    private String email;

    private String description;
    private String position;
    private String avatarUrl;
    protected Instant createdAt;
    private String country;
    private String city;
    private String address;
    private String about;
}

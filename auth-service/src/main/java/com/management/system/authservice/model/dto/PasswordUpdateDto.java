package com.management.system.authservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PasswordUpdateDto {

    @JsonProperty(required = true)
    @NotEmpty(message = "uuid must not be empty")
    @NotNull(message = "uuid must not be null")
    @NotBlank(message = "uuid must not be blank")
    private UUID uuid;

    @JsonProperty(required = true)
    @NotEmpty(message = "old password must not be empty")
    @NotBlank(message = "old password not be blank")
    private String oldPassword;

    @JsonProperty(required = true)
    @NotEmpty(message = "new password not be empty")
    @NotBlank(message = "new password not be blank")
    private String newPassword;

    @JsonProperty(required = true)
    @NotEmpty(message = "repeat password not be empty")
    @NotBlank(message = "repeat password not be blank")
    private String repeatPassword;
}

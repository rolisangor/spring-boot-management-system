package com.managementsystem.profileservice.service.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UuidValidator implements ConstraintValidator<ValidUuid, UUID> {

    @Override
    public void initialize(ValidUuid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext context) {
        String uuidRegex = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$";
        return uuid.toString().matches(uuidRegex);
    }
}

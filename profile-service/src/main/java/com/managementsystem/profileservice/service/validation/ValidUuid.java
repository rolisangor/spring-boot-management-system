package com.managementsystem.profileservice.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, LOCAL_VARIABLE, CONSTRUCTOR})
@Constraint(validatedBy={UuidValidator.class})
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface ValidUuid {
    String message() default "{invalid.uuid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

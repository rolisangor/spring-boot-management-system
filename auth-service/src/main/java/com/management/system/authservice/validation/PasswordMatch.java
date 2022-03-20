package com.management.system.authservice.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy=PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {

    String message() default "Error password mismatch";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String first();
    String second();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List
    {
        PasswordMatch[] value();
    }
}

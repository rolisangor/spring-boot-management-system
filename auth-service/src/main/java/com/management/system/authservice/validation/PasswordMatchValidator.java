package com.management.system.authservice.validation;

import com.management.system.authservice.model.dto.PasswordUpdateDto;
import com.management.system.authservice.model.dto.RegistrationDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, RegistrationDto> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(final PasswordMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(RegistrationDto value, ConstraintValidatorContext context) {
        boolean valid = true;
        try
        {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

            valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore) { }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}

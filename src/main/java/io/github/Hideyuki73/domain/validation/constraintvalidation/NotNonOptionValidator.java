package io.github.Hideyuki73.domain.validation.constraintvalidation;

import io.github.Hideyuki73.domain.validation.NotNonOption;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNonOptionValidator implements ConstraintValidator<NotNonOption, String> {
    @Override
    public boolean isValid(String o, ConstraintValidatorContext constraintValidatorContext) {
        return o.equals("CANCELADO") || o.equals("REALIZADO");
    }
}

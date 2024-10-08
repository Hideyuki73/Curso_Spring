package io.github.Hideyuki73.domain.validation;

import io.github.Hideyuki73.domain.validation.constraintvalidation.NotEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyValidator.class)
public @interface NotEmptyList {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "A lista nao pode estar vazia";
}

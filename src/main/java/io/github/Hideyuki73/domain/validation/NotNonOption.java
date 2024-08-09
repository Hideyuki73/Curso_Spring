package io.github.Hideyuki73.domain.validation;

import io.github.Hideyuki73.domain.validation.constraintvalidation.NotEmptyValidator;
import io.github.Hideyuki73.domain.validation.constraintvalidation.NotNonOptionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotNonOptionValidator.class)
public @interface NotNonOption {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Pedido so pode ser alterado para CANCELADO ou REALIZADO";
}

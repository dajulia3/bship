package com.bship.games.domains.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

@Target({ElementType.FIELD, ElementType.TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BoundsCheckValidation.class)
public @interface BoundsCheck {
    String message() default "out of bounds.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package com.yoti.hoover.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InstructionValidator.class)
public @interface InstructionConstraint {
    String message() default "Invalid instruction";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
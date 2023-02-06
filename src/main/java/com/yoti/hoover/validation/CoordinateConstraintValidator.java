package com.yoti.hoover.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.nonNull;

public class CoordinateConstraintValidator implements ConstraintValidator<CoordinateConstraint, int[]> {
    @Override
    public boolean isValid(int[] coords, ConstraintValidatorContext context) {
        return (nonNull(coords) && coords.length == 2 && coords[0] >= 0 && coords[1] >= 0);
    }
}

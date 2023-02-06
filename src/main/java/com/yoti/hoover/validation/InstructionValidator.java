package com.yoti.hoover.validation;

import com.yoti.hoover.service.Direction;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InstructionValidator implements ConstraintValidator<InstructionConstraint, String> {

    @Override
    public boolean isValid(String instructions, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasText(instructions)) {
            return false;
        }

        final char[] charArray = instructions.toCharArray();
        try {
            for (char instruction : charArray) {
                Direction.getType(instruction);
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

}
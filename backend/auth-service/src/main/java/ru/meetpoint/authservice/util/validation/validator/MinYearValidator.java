package ru.meetpoint.authservice.util.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.meetpoint.authservice.util.validation.annotation.MinYear;

import java.time.LocalDate;

public class MinYearValidator implements ConstraintValidator<MinYear, LocalDate> {

    private int minYear;

    @Override
    public void initialize(MinYear constraintAnnotation) {
        this.minYear = constraintAnnotation.year();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return true;
        }

        LocalDate minDate = LocalDate.of(minYear, 1, 1);
        return localDate.isAfter(minDate);
    }
}

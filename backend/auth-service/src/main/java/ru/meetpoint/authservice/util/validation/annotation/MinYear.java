package ru.meetpoint.authservice.util.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.meetpoint.authservice.util.validation.validator.MinYearValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinYearValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface MinYear {

    int year() default 1900;

    String message() default "Date should de after {year} year!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

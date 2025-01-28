package com.happyaging.fallprevention.senior;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MySeniorValidator.class)
public @interface MySenior {
	String message() default "Invalid senior ID: Not your senior";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

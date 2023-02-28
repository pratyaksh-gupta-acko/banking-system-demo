package com.backend.bank.banking_system.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TransactionValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionConstraint {
    String message() default "Invalid Transaction";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

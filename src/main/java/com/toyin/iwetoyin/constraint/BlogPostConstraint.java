package com.toyin.iwetoyin.constraint;

import com.toyin.iwetoyin.validation.BlogPostValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = BlogPostValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BlogPostConstraint {
    String message() default "Invalid Blog post";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

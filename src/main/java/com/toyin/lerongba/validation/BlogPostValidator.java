package com.toyin.lerongba.validation;

import org.jsoup.Jsoup;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BlogPostValidator implements ConstraintValidator<BlogPostConstraint, String> {

    @Override
    public void initialize(BlogPostConstraint constraintAnnotation) {}

    @Override
    public boolean isValid(String rawContent, ConstraintValidatorContext constraintValidatorContext) {
        String content = Jsoup.parse(rawContent).text();
        return content.length() >= 200;
    }
}

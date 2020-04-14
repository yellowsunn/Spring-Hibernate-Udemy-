package com.yellowsunn.mvc.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CourseCodeConstraintValidator implements ConstraintValidator<CourseCode, String> {
    private String[] coursePrefixes;

    @Override
    public void initialize(CourseCode courseCode) {
        coursePrefixes = courseCode.value();
    }

    @Override
    public boolean isValid(String theCode, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;

        if (theCode != null) {
            for (String tempPrefix : coursePrefixes) {
                result = theCode.startsWith(tempPrefix);

                // if we found a match then break out of the loop
                if (result) {
                    break;
                }
            }
        } else {
            result = true;
        }

        return result;
    }
}

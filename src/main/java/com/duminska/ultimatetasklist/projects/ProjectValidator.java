package com.duminska.ultimatetasklist.projects;

import com.duminska.ultimatetasklist.exception.ValidationException;
import org.springframework.util.StringUtils;

public class ProjectValidator {

    private static final String EMPTY_PROPERTY_EXCEPTION_MESSAGE = "User field parameter '%s' must be provided";

    public static void validate(DtoProject project) throws ValidationException {
        validateNotEmptyProperty(project.getName(), "project name");
        System.out.println(project.getName());
        validateNotEmptyProperty(project.getUserId(), "user id");
    }

    private static void validateNotEmptyProperty(Object value, String propertyName) {
        if (value == null || StringUtils.isEmpty(value)) {
            throw new ValidationException(String.format(EMPTY_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }

}

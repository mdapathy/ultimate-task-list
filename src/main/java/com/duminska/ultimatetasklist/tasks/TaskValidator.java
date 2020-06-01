package com.duminska.ultimatetasklist.tasks;

import com.duminska.ultimatetasklist.exception.ValidationException;
import org.springframework.util.StringUtils;

public class TaskValidator {

    private static final String EMPTY_PROPERTY_EXCEPTION_MESSAGE = "User field parameter '%s' must be provided";


    public static void validate(DtoTask task) throws ValidationException {
        validateNotEmptyProperty(task.getName(), "task name");
        validateNotEmptyProperty(task.getProjectId(), "project id");
    }

    private static void validateNotEmptyProperty(Object value, String propertyName) {
        if (value == null || StringUtils.isEmpty(value)) {
            throw new ValidationException(String.format(EMPTY_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }

}

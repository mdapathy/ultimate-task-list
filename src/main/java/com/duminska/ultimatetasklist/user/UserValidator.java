package com.duminska.ultimatetasklist.user;

import com.duminska.ultimatetasklist.exception.ValidationException;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String EMPTY_PROPERTY_EXCEPTION_MESSAGE = "User field parameter '%s' must be provided";

    private static final String REGEX_EXCEPTION_MESSAGE = "User field parameter '%s' must match these parameters: '%s'";

    private static final String REGEX_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";


    public static void validate(DtoUserAuth user) throws ValidationException {
        validateNotEmptyProperty(user.getPassword(), "password");
        validateNotEmptyProperty(user.getEmail(), "email");
        validateWithRegularExpression(user.getEmail(), REGEX_EMAIL, "email");
    }

    private static void validateNotEmptyProperty(Object value, String propertyName) {
        if (value == null || StringUtils.isEmpty(value)) {
            throw new ValidationException(String.format(EMPTY_PROPERTY_EXCEPTION_MESSAGE, propertyName));
        }
    }

    private static void validateWithRegularExpression(Object value, String regex, String propertyName) {

        Matcher matcher = Pattern.compile(regex).matcher(String.valueOf(value));
        if (!matcher.matches()) {
            throw new ValidationException(String.format(REGEX_EXCEPTION_MESSAGE, propertyName, regex));
        }

    }
}

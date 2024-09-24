package org.jquijano.proyects.MovieManagement.persistence.service.validator;

import org.jquijano.proyects.MovieManagement.exception.InvalidPasswordException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class PasswordValidator {

    public static void validatePassword(String password, String passwordRepeated) {
        if (!StringUtils.hasText(password) || !StringUtils.hasText(passwordRepeated)) {
            throw new IllegalArgumentException("Password Must Contains Data");
        }

        if (!password.equals(passwordRepeated)) {
            throw new InvalidPasswordException(password, passwordRepeated, "Passwords do not match");
        }

        if (!containsNumber(password)) {
            throw new InvalidPasswordException(password, "Passwords must contain at least one number");
        }

        if (!containsUpperCase(password)) {
            throw new InvalidPasswordException(password, "Passwords must contain at least one uppercase letter");
        }

        if (!containsLowerCase(password)) {
            throw new InvalidPasswordException(password, "Passwords must contain at least one lowercase letter");
        }

        if (!containsSpecialCharacters(password)) {
            throw new InvalidPasswordException(password, "Passwords must contain at least one special character  ");
        }
    }

    private static boolean containsSpecialCharacters(String password) {
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*");
    }

    private static boolean containsLowerCase(String password) {
        return password.matches(".*[a-z].*");
    }

    private static boolean containsUpperCase(String password) {
        return password.matches(".*[A-Z].*");
    }

    private static boolean containsNumber(String password) {
        return password.matches(".*\\d.*");
    }

}

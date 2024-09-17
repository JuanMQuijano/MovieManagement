package org.jquijano.proyects.MovieManagement.exception;

public class InvalidPasswordException extends RuntimeException {

    private String password;
    private String passwordRepeated;
    private String errorDescription;

    public InvalidPasswordException(String password, String errorDescription) {
        this.password = password;
        this.errorDescription = errorDescription;
    }

    public InvalidPasswordException(String password, String passwordRepeated, String errorDescription) {
        this.password = password;
        this.passwordRepeated = passwordRepeated;
        this.errorDescription = errorDescription;
    }

    @Override
    public String getMessage() {
        return "Invalid Password " + errorDescription;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}

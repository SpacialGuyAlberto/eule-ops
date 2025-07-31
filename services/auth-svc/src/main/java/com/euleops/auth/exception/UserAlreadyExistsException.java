package com.euleops.auth.exception;

public class UserAlreadyExistsException extends AuthException {
    public UserAlreadyExistsException(String username) {
        super("User with email '" + username + "' already exists.");
    }
}

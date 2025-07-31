package com.euleops.auth.exception;

public class AccountNotActiveException extends RuntimeException {
    public AccountNotActiveException(String username) {
        super("User with  username'" + username + "' not active yet. Please activate your account.");
    }
}

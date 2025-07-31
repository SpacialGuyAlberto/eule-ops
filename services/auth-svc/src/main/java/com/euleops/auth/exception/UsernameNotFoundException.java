package com.euleops.auth.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String username) {
        super("User with  username'" + username + "' not found.");
    }
}

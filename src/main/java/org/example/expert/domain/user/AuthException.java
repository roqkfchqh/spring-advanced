package org.example.expert.domain.user;

public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }
}

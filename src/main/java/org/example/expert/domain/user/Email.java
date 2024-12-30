package org.example.expert.domain.user;

import jakarta.persistence.Column;
import lombok.Value;
import org.example.expert.domain.exception.InvalidRequestException;

@Value
public class Email {

    @Column(nullable = false, unique = true)
    String value;

    private Email(String value) {
        validate(value);
        this.value = value;
    }

    public static Email of(String value) {
        return new Email(value);
    }

    private void validate(String value) {
        if (value == null || !value.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new InvalidRequestException("Invalid email format: " + value);
        }
    }
}

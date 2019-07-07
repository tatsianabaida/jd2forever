package com.itacademy.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailExistsException extends RuntimeException {

    public EmailExistsException(String message) {
        super(message);
    }
}

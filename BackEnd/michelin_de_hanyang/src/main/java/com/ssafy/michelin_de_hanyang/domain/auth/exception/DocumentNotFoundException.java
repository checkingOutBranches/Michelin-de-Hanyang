package com.ssafy.michelin_de_hanyang.domain.auth.exception;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(String message) {
        super(message);
    }
}
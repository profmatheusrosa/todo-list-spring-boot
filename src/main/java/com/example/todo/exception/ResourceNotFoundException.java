package com.example.todo.exception;

/**
 * Exceção personalizada para representar recursos não encontrados (Status 404).
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

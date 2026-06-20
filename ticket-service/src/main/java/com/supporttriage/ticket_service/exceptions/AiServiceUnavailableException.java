package com.supporttriage.ticket_service.exceptions;

public class AiServiceUnavailableException extends RuntimeException {
    
    public AiServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}

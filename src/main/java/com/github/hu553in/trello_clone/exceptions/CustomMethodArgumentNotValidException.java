package com.github.hu553in.trello_clone.exceptions;

public class CustomMethodArgumentNotValidException extends Exception {
    private final String field;
    private final String message;

    public CustomMethodArgumentNotValidException(final String field, final String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

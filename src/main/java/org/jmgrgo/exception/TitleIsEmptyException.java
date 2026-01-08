package org.jmgrgo.exception;

public class TitleIsEmptyException extends RuntimeException {
    public TitleIsEmptyException(String message) {
        super(message);
    }
}

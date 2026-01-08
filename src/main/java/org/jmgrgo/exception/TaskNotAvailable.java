package org.jmgrgo.exception;

public class TaskNotAvailable extends RuntimeException {
    public TaskNotAvailable(String message) {
        super(message);
    }
}

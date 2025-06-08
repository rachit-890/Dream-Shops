package org.dailycodework.dreamshops.exception;

import org.dailycodework.dreamshops.model.Category;

public class AlreadyExitsException extends RuntimeException {
    public AlreadyExitsException(String message) {
        super(message);
    }
}

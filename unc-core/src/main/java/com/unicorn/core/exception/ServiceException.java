package com.unicorn.core.exception;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -9205222980576132209L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

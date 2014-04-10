package com.puduhe.exception.io;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 5519046612491757341L;

    public ServiceException() {
        super("业务异常");
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}

package com.puduhe.exception.io;


public class DAException extends Exception {

    private static final long serialVersionUID = 1495935505616410300L;

    public DAException() {
        super();
    }

    public DAException(String message) {
        super(message);
    }

    public DAException(Throwable cause) {
        super(cause);
    }

    public DAException(String message, Throwable cause) {
        super(message, cause);
    }

}

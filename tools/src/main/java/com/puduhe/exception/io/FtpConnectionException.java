package com.puduhe.exception.io;


import java.io.IOException;

public class FtpConnectionException extends IOException {
    private static final long serialVersionUID = 6959475605337955329L;

    public FtpConnectionException() {
        super("FTP server refused connection.");
    }

    public FtpConnectionException(String message) {
        super(message);
    }

    public FtpConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FtpConnectionException(Throwable cause) {
        super(cause);
    }
}

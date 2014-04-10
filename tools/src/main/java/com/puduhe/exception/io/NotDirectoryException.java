package com.puduhe.exception.io;


import java.io.File;

public class NotDirectoryException extends Exception {
    private static final long serialVersionUID = -1562765846871480884L;

    public NotDirectoryException() {
        super("this is not a directory");
    }

    public NotDirectoryException(String message) {
        super(message);
    }

    public NotDirectoryException(File directory) {
        super(directory.getAbsolutePath() + " is not a directory");
    }
}

package com.puduhe.exception.io;


import java.io.File;

public class NotFileException extends Exception {
    private static final long serialVersionUID = -1562765846871480884L;

    public NotFileException() {
        super("this is not a file");
    }

    public NotFileException(String message) {
        super(message);
    }

    public NotFileException(File file) {
        super(file.getAbsolutePath() + " is not a file");
    }
}

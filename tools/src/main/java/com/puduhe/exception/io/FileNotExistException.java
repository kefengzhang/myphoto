package com.puduhe.exception.io;

import com.puduhe.util.io.Directory;


public class FileNotExistException extends Exception {

    private static final long serialVersionUID = -5961839745403129241L;

    public FileNotExistException() {
        super("the file is not existed");
    }

    public FileNotExistException(String message) {
        super(message);
    }

    public FileNotExistException(Directory dir) {
        super(dir.getAbsolutePath() + " is not existed");
    }

}

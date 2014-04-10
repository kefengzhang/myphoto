package com.puduhe.exception.io;

import com.puduhe.util.io.Directory;



public class DirectoryNotExistException extends Exception {

    private static final long serialVersionUID = -5961839745403129241L;

    public DirectoryNotExistException() {
        super("the directory is not existed");
    }

    public DirectoryNotExistException(String message) {
        super(message);
    }

    public DirectoryNotExistException(Directory dir) {
        super(dir.getAbsolutePath() + " is not existed");
    }

}

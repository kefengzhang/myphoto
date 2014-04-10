package com.puduhe.exception.io;

import com.puduhe.util.io.Directory;




public class DirectoryAlreadyExistException extends Exception {

    private static final long serialVersionUID = 2305644126116519969L;

    public DirectoryAlreadyExistException() {
        super("the directory is already existed");
    }

    public DirectoryAlreadyExistException(String message) {
        super(message);
    }

    public DirectoryAlreadyExistException(Directory dir) {
        super(dir.getAbsolutePath() + " is already existed");
    }
}

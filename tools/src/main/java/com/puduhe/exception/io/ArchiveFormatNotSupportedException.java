package com.puduhe.exception.io;


public class ArchiveFormatNotSupportedException extends Exception {
    private static final long serialVersionUID = -7940754260760623566L;

    public ArchiveFormatNotSupportedException() {
        super("the archive format is not supported");
    }

    public ArchiveFormatNotSupportedException(String message) {
        super(message);
    }
}

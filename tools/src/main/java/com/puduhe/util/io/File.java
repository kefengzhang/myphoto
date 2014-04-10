package com.puduhe.util.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.puduhe.exception.io.NotFileException;


public class File extends java.io.File {
    private static final long serialVersionUID = -5555980406177477112L;
    private InputStream inputStream;
    private OutputStream outputStream;

    /**
     * 构造函数，文件路径名末尾不能以分隔符结束
     *
     * @param pathname
     *
     * @throws NotFileException
     */
    public File(String pathname) throws NotFileException {
        super(pathname);
        if (pathname.endsWith(File.separator)) {
            throw new NotFileException(this);
        }
    }
    
    public File(File parent, String child) {
    	super(parent,child);
    }
    
    /**
     * 构造函数，文件路径名末尾不能以分隔符结束
     *
     * @param pathname
     *
     * @throws NotFileException
     */
    public File(String pathname,String pathname2) throws NotFileException {
        super(pathname,pathname2);
        if (pathname.endsWith(File.separator)) {
            throw new NotFileException(this);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * 构造函数，文件路径名末尾不能以分隔符结束
     *
     * @param file
     *
     * @throws NotFileException
     */
    public File(java.io.File file) throws NotFileException {
        this(file.getAbsolutePath());
    }

    /** @return the is */
    public InputStream getInputStream() {
        if (inputStream == null) {
            try {
                inputStream = new FileInputStream(this);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return inputStream;
    }

    /** @param is to set */
    public void setInputStream(InputStream is) {
        this.inputStream = null;
        this.inputStream = is;
    }

    /** @return the os */
    public OutputStream getOutputStream() {
        if (outputStream == null) {
            try {
                outputStream = new FileOutputStream(this);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return outputStream;
    }

    /** @param os to set */
    public void setOutputStream(OutputStream os) {
        this.outputStream = null;
        this.outputStream = os;
    }

    /** 关闭文件 */
    public void close() {
        if (this.inputStream != null) {
            try {
                this.inputStream.close();
            } catch (IOException e) {
            }
            this.inputStream = null;
        }
        if (this.outputStream != null) {
            try {
                this.outputStream.close();
            } catch (IOException e) {
            }
            this.outputStream = null;
        }
    }
}

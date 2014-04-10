package com.puduhe.util.io;


import java.io.File;

import com.puduhe.exception.io.DirectoryNotExistException;
import com.puduhe.exception.io.NotDirectoryException;



public class Directory extends File {

    private static final long serialVersionUID = 7988396767633359981L;

    /**
     * 构造函数，文件夹路径名末尾必须以分隔符结束
     *
     * @param pathname
     *
     * @throws NotDirectoryException
     */
    public Directory(String pathname) throws NotDirectoryException {
        super(pathname);
        if (!pathname.endsWith(File.separator)) {
            throw new NotDirectoryException(this);
        }
    }

    public Directory(java.io.File file) throws NotDirectoryException {
        this(file.getAbsolutePath());
    }

    public String getAbsoluteDirPath() {
        return this.getAbsolutePath() + File.separator;
    }

    /**
     * 判断文件夹是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        boolean result;
        if (this.isDirectory()) {
            String[] pathnames = this.list();
            if (pathnames.length == 0) {
                result = true;
            } else {
                result = false;
            }
        } else {
            try {
                throw new DirectoryNotExistException(this);
            } catch (DirectoryNotExistException e) {
                e.printStackTrace();
            }
            result = false;
		}
		return result;
	}
}

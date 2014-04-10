package com.puduhe.util.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import com.puduhe.exception.io.DirectoryAlreadyExistException;
import com.puduhe.exception.io.DirectoryNotExistException;
import com.puduhe.exception.io.FileNotExistException;
import com.puduhe.exception.io.NotDirectoryException;
import com.puduhe.exception.io.NotFileException;
import com.puduhe.util.SecurityFactory;


public class FileUtil {
	/**
	 * 获得当前资源文件的路径
	 * 
	 * @param c
	 * 
	 * @return
	 */
	public static String getClassPath(Class<?> c) {
		String path = null;
		try {
			path = c.getResource("/").toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 判断文件或文件夹是否存在
	 * 
	 * @param pathname
	 * 
	 * @return
	 */
	public static boolean isExist(String pathname) {
		boolean isExist = false;
		if (pathname.endsWith(File.separator)) {
			Directory dir = null;
			try {
				dir = new Directory(pathname);
				isExist = dir.isDirectory();
			} catch (NotDirectoryException e) {
				e.printStackTrace();
				isExist = false;
			} finally {
				dir = null;
			}
		} else {
			File file = null;
			try {
				file = new File(pathname);
				isExist = file.isFile();
			} catch (NotFileException e) {
				e.printStackTrace();
				isExist = false;
			} finally {
				if (file != null) {
					file.close();
				}
				file = null;
			}
		}
		return isExist;
	}

	/**
	 * 获得文件的文件类型，如果没有文件类型，则返回null
	 * 
	 * @param pathname
	 * 
	 * @return
	 */
	public static String getType(String pathname) {
		String suffix = null;
		String[] name = pathname.split("\\.");
		if (name.length > 1) {
			suffix = name[name.length - 1];
		}
		name = null;
		return suffix;
	}

	/**
	 * 获得文件的文件类型，如果没有文件类型，则返回null
	 * 
	 * @param file
	 * 
	 * @return
	 */
	public static String getType(File file) {
		return getType(file.toURI().getPath());
	}

	/**
	 * 获得pathname中的路径
	 * 
	 * @param pathname
	 * 
	 * @return
	 */
	public static String getPath(String pathname) {
		int point = pathname.lastIndexOf(File.separator);
		int length = pathname.length();
		if (point == -1) {
			return null;
		} else if (point == length - 1) {
			int secondPoint = pathname.lastIndexOf(File.separator, point - 1);
			if (secondPoint == -1) {
				return null;
			} else {
				return pathname.substring(0, secondPoint);
			}
		} else {
			return pathname.substring(0, point);
		}
	}

	public static String getPath(File file) {
		return getPath(file.toURI().getPath());
	}

	/**
	 * 获得pathname中的文件名
	 * 
	 * @param pathname
	 * 
	 * @return
	 */
	public static String getName(String pathname) {
		return new java.io.File(pathname).getName();
	}

	/**
	 * 打开文件，返回文件输入流
	 * 
	 * @return
	 */
	public static InputStream openFile(File file) {
		return file.getInputStream();

	}

	/**
	 * 打开给定的文件列表，返回输入流列表
	 * 
	 * @param fileNames
	 * 
	 * @return
	 */
	public static List<InputStream> openFiles(String[] fileNames) {
		int size = fileNames.length;
		List<File> fileList = new ArrayList<File>();
		List<InputStream> isList;
		for (int i = 0; i < size; i++) {
			try {
				fileList.add(new File(fileNames[i]));
			} catch (NotFileException e) {
				e.printStackTrace();
			}
		}
		isList = openFiles(fileList);
		fileList.clear();
		fileList = null;
		return isList;
	}

	/**
	 * 打开给定的文件列表，返回输入流列表
	 * 
	 * @param fileList
	 * 
	 * @return
	 */
	public static List<InputStream> openFiles(List<File> fileList) {
		List<InputStream> streamList = new ArrayList<InputStream>();
		Iterator<File> it = fileList.iterator();
		while (it.hasNext()) {
			InputStream is = openFile(it.next());
			streamList.add(is);
			is = null;
		}
		it = null;
		return streamList;
	}

	/**
	 * 保存给定的输入流到文件
	 * 
	 * @param file
	 * @param is
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	public static boolean saveFile(File file, InputStream is)
			throws IOException {
		file.setInputStream(is);
		return saveFile(file);
	}

	/**
	 * 把文件保存到给定的输出流
	 * 
	 * @param file
	 * @param os
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	public static boolean saveFile(File file, OutputStream os)
			throws IOException {
		file.setOutputStream(os);
		return saveFile(file);
	}

	/**
	 * 保存文件
	 * 
	 * @param file
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */
	public static boolean saveFile(File file) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			makeDirectory(file.getParent());
			is = new BufferedInputStream(file.getInputStream());
			os = file.getOutputStream();
			byte[] buf = new byte[1024];
			int readLen = 0;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, readLen);
			}
		} finally {
			if (is != null) {
				is.close();
				is = null;
			}
			if (os != null) {
				os.close();
				os = null;
			}
		}
		return true;
	}

	/**
	 * 保存文件列表
	 * 
	 * @param fileList
	 * 
	 * @return
	 */
	public static int saveFiles(List<File> fileList) {
		int savedCount = 0;
		Iterator<File> it = fileList.iterator();
		boolean isSaved;
		while (it.hasNext()) {
			try {
				isSaved = saveFile(it.next());
			} catch (IOException e) {
				isSaved = false;
			}
			if (isSaved) {
				savedCount++;
			}
		}
		it = null;
		return savedCount;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * 
	 * @return
	 * 
	 * @throws NotFileException
	 */
	public static boolean deleteFile(String fileName) {
		File file;
		boolean result = false;
		try {
			file = new File(fileName);
			result = deleteFile(file);
		} catch (NotFileException e) {
			e.printStackTrace();
		}

		file = null;
		return result;
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 * 
	 * @return
	 */
	public static boolean deleteFile(File file) {
		boolean isDeleted = false;
		if (file.isFile()) {
			file.close();
			isDeleted = file.delete();
		} else {
			try {
				throw new FileNotExistException();
			} catch (FileNotExistException e) {
				e.printStackTrace();
			}
		}
		return isDeleted;
	}

	/**
	 * 删除文件列表
	 * 
	 * @param fileNames
	 * 
	 * @return
	 */
	public static int deleteFiles(String[] fileNames) {
		int size = fileNames.length;
		List<File> fileList = new ArrayList<File>();
		for (int i = 0; i < size; i++) {
			try {
				fileList.add(new File(fileNames[i]));
			} catch (NotFileException e) {
				e.printStackTrace();
			}
		}
		int count = deleteFiles(fileList);
		fileList.clear();
		fileList = null;
		return count;
	}

	/**
	 * 删除文件列表
	 * 
	 * @param fileList
	 * 
	 * @return
	 */
	public static int deleteFiles(List<File> fileList) {
		int deletedCount = 0;
		Iterator<File> it = fileList.iterator();
		boolean isDeleted;
		while (it.hasNext()) {
			isDeleted = it.next().delete();
			if (isDeleted) {
				deletedCount++;
			}
		}
		return deletedCount;
	}

	/**
	 * 复制文件
	 * 
	 * @param sourcePathname
	 * @param targetPathname
	 * 
	 * @return
	 */
	public static boolean copyFile(String sourcePathname, String targetPathname) {
		boolean result = false;
		File source = null;
		File target = null;
		try {
			source = new File(sourcePathname);
			target = new File(targetPathname);
			result = copyFile(source, target);
			source.close();
			source = null;
			target.close();
			target = null;
		} catch (NotFileException e) {
			e.printStackTrace();
		} finally {
			if (source != null) {
				source.close();
				source = null;
			}
			if (target != null) {
				target.close();
				target = null;
			}
		}
		return result;
	}

	/**
	 * 复制文件
	 * 
	 * @param sourcePathname
	 * @param target
	 * 
	 * @return
	 */
	public static boolean copyFile(String sourcePathname, File target) {
		boolean result = false;
		File source = null;
		try {
			source = new File(sourcePathname);
			result = copyFile(source, target);
			source.close();
			source = null;
		} catch (NotFileException e) {
			e.printStackTrace();
		} finally {
			if (source != null) {
				source.close();
				source = null;
			}
		}
		return result;
	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 * @param targetPathname
	 * 
	 * @return
	 */
	public static boolean copyFile(File source, String targetPathname) {
		boolean result = false;
		File target = null;
		try {
			target = new File(targetPathname);
			result = copyFile(source, target);
			target.close();
			target = null;
		} catch (NotFileException e) {
			e.printStackTrace();
		} finally {
			if (target != null) {
				target.close();
				target = null;
			}
		}
		return result;
	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 * @param target
	 * 
	 * @return
	 */
	public static boolean copyFile(File source, File target) {
		boolean result = false;
		target.setInputStream(source.getInputStream());
		try {
			result = saveFile(target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 移动文件
	 * 
	 * @param sourcePathname
	 * @param targetPathname
	 * 
	 * @return
	 */
	public static boolean moveFile(String sourcePathname, String targetPathname) {
		boolean result = false;
		File source = null;
		File target = null;
		try {
			source = new File(sourcePathname);
			target = new File(targetPathname);
			result = moveFile(source, target);
			source.close();
			source = null;
			target.close();
			target = null;
		} catch (NotFileException e) {
			e.printStackTrace();
		} finally {
			if (source != null) {
				source.close();
				source = null;
			}
			if (target != null) {
				target.close();
				target = null;
			}
		}
		return result;
	}

	/**
	 * 移动文件
	 * 
	 * @param sourcePathname
	 * @param target
	 * 
	 * @return
	 */
	public static boolean moveFile(String sourcePathname, File target) {
		boolean result = false;
		File source = null;
		try {
			source = new File(sourcePathname);
			result = moveFile(source, target);
			source.close();
			source = null;
		} catch (NotFileException e) {
			e.printStackTrace();
		} finally {
			if (source != null) {
				source.close();
				source = null;
			}
		}
		return result;
	}

	/**
	 * 移动文件
	 * 
	 * @param source
	 * @param targetPathname
	 * 
	 * @return
	 */
	public static boolean moveFile(File source, String targetPathname) {
		boolean result = false;
		File target = null;
		try {
			target = new File(targetPathname);
			result = moveFile(source, target);
			target.close();
			target = null;
		} catch (NotFileException e) {
			e.printStackTrace();
		} finally {
			if (target != null) {
				target.close();
				target = null;
			}
		}
		return result;
	}

	/**
	 * 移动文件
	 * 
	 * @param source
	 * @param target
	 * 
	 * @return
	 */
	public static boolean moveFile(File source, File target) {
		boolean result;
		result = copyFile(source, target);
		if (result) {
			result = source.delete();
		}
		return result;
	}

	/**
	 * 关闭输入流列表
	 * 
	 * @param isList
	 */
	public static void closeInputStream(List<?> isList) {
		Iterator<?> it = isList.iterator();
		while (it.hasNext()) {
			InputStream is = (InputStream) it.next();
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			is = null;
		}
		it = null;
	}

	/**
	 * 关闭输出流列表
	 * 
	 * @param osList
	 */
	public static void closeOutputStream(List<?> osList) {
		Iterator<?> it = osList.iterator();
		while (it.hasNext()) {
			OutputStream os = (OutputStream) it.next();
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			os = null;
		}
		it = null;
	}

	/**
	 * 关闭文件列表
	 * 
	 * @param fileList
	 */
	public static void closeFiles(List<File> fileList) {
		int size = fileList.size();
		for (int i = 0; i < size; i++) {
			fileList.get(i).close();
		}
	}

	/**
	 * 改变文件夹路径
	 * 
	 * @param pathname
	 * 
	 * @return
	 */
	public static Directory changeDirectory(String pathname) {
		Directory dir = null;
		if (!pathname.endsWith(File.separator)) {
			pathname += File.separator;
		}
		try {
			dir = new Directory(pathname);
			if (!dir.isDirectory()) {
				throw new DirectoryNotExistException(dir);
			}
		} catch (NotDirectoryException e) {
			e.printStackTrace();
			dir = null;
		} catch (DirectoryNotExistException e) {
			e.printStackTrace();
			dir = null;
		}

		return dir;
	}

	/**
	 * 在当前dir路径下，改变路径
	 * 
	 * @param dir
	 * @param pathname
	 * 
	 * @return
	 */
	public static Directory changeDirectory(Directory dir, String pathname) {
		String newPathname = dir.getAbsoluteDirPath() + pathname;
		return changeDirectory(newPathname);
	}

	/**
	 * 创建文件夹
	 * 
	 * @param pathname
	 * 
	 * @return
	 */
	public static Directory makeDirectory(String pathname) {
		if (!pathname.endsWith(File.separator)) {
			pathname += File.separator;
		}
		Directory dir;
		try {
			dir = new Directory(pathname);
			if (dir.isDirectory()) {
				throw new DirectoryAlreadyExistException(dir);
			} else {
				dir.mkdirs();
			}
		} catch (NotDirectoryException e) {
			dir = null;
		} catch (DirectoryAlreadyExistException e) {
			dir = null;
		}

		return dir;
	}

	/**
	 * 在当前dir路径下，创建文件夹
	 * 
	 * @param dir
	 * @param pathname
	 * 
	 * @return
	 */
	public static Directory makeDirectory(Directory dir, String pathname) {
		String newPathname = dir.getAbsoluteDirPath() + pathname;
		return makeDirectory(newPathname);
	}

	/**
	 * 移除文件夹
	 * 
	 * @param pathname
	 * 
	 * @return
	 * 
	 * @throws NotDirectoryException
	 */
	public static boolean removeDirectory(String pathname) {
		Directory dir = changeDirectory(pathname);
		return removeDirectory(dir);
	}

	/**
	 * 移除文件夹
	 * 
	 * @param dir
	 * 
	 * @return
	 */
	public static boolean removeDirectory(Directory dir) {
		boolean result = true;
		boolean isDeleted;
		File chlidFile = null;
		Directory childDir = null;
		if (dir.isDirectory()) {
			String[] filenames = dir.list();
			for (int i = 0; i < filenames.length; i++) {
				String pathname = dir.getAbsoluteDirPath() + filenames[i];
				try {
					chlidFile = new File(pathname);
					if (chlidFile.isFile()) {
						isDeleted = chlidFile.delete();
					} else {
						throw new NotFileException();
					}
				} catch (NotFileException e) {
					try {
						pathname += "\\";
						childDir = new Directory(pathname);
						isDeleted = removeDirectory(childDir);
					} catch (NotDirectoryException e1) {
						isDeleted = false;
					}
				} finally {
					chlidFile = null;
					childDir = null;
					pathname = null;
				}
			}
			isDeleted = dir.delete();
			if (!isDeleted) {
				result = false;
			}
		} else {
			try {
				throw new DirectoryNotExistException(dir);
			} catch (DirectoryNotExistException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 列出文件夹中的所有文件
	 * 
	 * @param dir
	 * 
	 * @return
	 */
	public static List<File> listFilesInDir(Directory dir) {
		List<File> fileList = new ArrayList<File>();
		String parentPathname = dir.getAbsoluteDirPath();
		String[] fileNames = dir.list();
		File childFile;
		for (int i = 0; i < fileNames.length; i++) {
			String fileName = parentPathname + fileNames[i];
			java.io.File tempFile = new java.io.File(fileName);
			if (tempFile.isFile()) {
				try {
					childFile = new File(fileName);
					fileList.add(childFile);
				} catch (NotFileException e) {
				}
			}
			childFile = null;
			tempFile = null;
		}
		fileNames = null;
		parentPathname = null;
		return fileList;
	}

	/**
	 * 列出文件夹中的所有文件夹
	 * 
	 * @param dir
	 * 
	 * @return
	 */
	public static List<Directory> listDirsInDir(Directory dir) {
		List<Directory> dirList = new ArrayList<Directory>();
		String parentPathname = dir.getAbsoluteDirPath();
		String[] dirNames = dir.list();
		Directory childDir;
		for (int i = 0; i < dirNames.length; i++) {
			StringBuilder fileName = new StringBuilder();
			fileName.append(parentPathname);
			fileName.append(dirNames[i]);
			java.io.File tempFile = new java.io.File(fileName.toString());
			if (tempFile.isDirectory()) {
				try {
					fileName.append(File.separator);
					childDir = new Directory(fileName.toString());
					dirList.add(childDir);
				} catch (NotDirectoryException e) {
				}
			}
			childDir = null;
			tempFile = null;
		}
		dirNames = null;
		parentPathname = null;
		return dirList;
	}

	/**
	 * 遍历一个目录，返回目录中所有文件（夹）的列表
	 * 
	 * @param dir
	 * 
	 * @return
	 */
	public static List<java.io.File> traversalDirectory(Directory dir) {
		List<java.io.File> fileList = new ArrayList<java.io.File>();
		List<File> childFileList = listFilesInDir(dir);
		fileList.addAll(childFileList);
		childFileList = null;
		List<Directory> childDirList = listDirsInDir(dir);
		int size = childDirList.size();
		Directory tempDir;
		for (int i = 0; i < size; i++) {
			tempDir = childDirList.get(i);
			fileList.addAll(traversalDirectory(tempDir));
			tempDir = null;
		}
		childDirList = null;
		return fileList;
	}

	/**
	 * 返回文件夹的大小
	 * 
	 * @param dir
	 * 
	 * @return
	 */
	public static long getDirLength(Directory dir) {
		long len = 0;
		List<java.io.File> fileList = traversalDirectory(dir);
		Iterator<java.io.File> it = fileList.iterator();
		while (it.hasNext()) {
			java.io.File file = it.next();
			len += file.length();
			file = null;
		}
		it = null;
		fileList = null;
		return len;
	}

	public static String trimType(String filename) {
		int index = filename.lastIndexOf(".");
		if (index != -1) {
			return filename.substring(0, index);
		} else {
			return filename;
		}
	}

	public static boolean checkCrc(String filename, String crc) {
		boolean isSuccess = false;

		File file = null;
		InputStream is = null;
		String myCrc = null;
		try {
			file = new File(filename);
			is = FileUtil.openFile(file);
			myCrc = SecurityFactory.CRC(is);
			if (myCrc.equalsIgnoreCase(crc)) {
				isSuccess = true;
			} else {
				isSuccess = false;
			}
		} catch (Exception e) {
			isSuccess = false;

		} finally {
			if (file != null) {
				file.close();
				file = null;
			}

			is = null;
			myCrc = null;
		}
		return isSuccess;
	}

	/**
	 * @param rootDirectory
	 * @param directoryName
	 * @param secondName
	 * @param fileName
	 * @return
	 * @throws NotFileException
	 */
	public static File creatFolder(String rootDirectory, String directoryName,
			String secondName, String fileName) throws NotFileException {
		File file = null;
		directoryName = directoryName.replaceAll("/", "");
		directoryName = directoryName.replaceAll(" ", "");
		directoryName = directoryName.replaceAll(" ", "");

		secondName = secondName.replaceAll("/", "");
		secondName = secondName.replaceAll(" ", "");
		secondName = secondName.replaceAll(" ", "");

		File firstFolder = new File(rootDirectory + directoryName);
		if (firstFolder.exists()) {
			File secondFolder = new File(firstFolder, secondName);
			if (secondFolder.exists()) {
				file = new File(secondFolder, fileName);
			} else {
				secondFolder.mkdir();
				file = new File(secondFolder, fileName);
			}
		} else {
			firstFolder.mkdirs();
			File secondFolder = new File(firstFolder, secondName);
			if (secondFolder.exists()) {
				file = new File(secondFolder, fileName);
			} else {
				secondFolder.mkdir();
				file = new File(secondFolder, fileName);
			}
		}
		return file;
	}

	public static void doCompressFile(String inFileName) {
		try {
			System.out.println("Creating the GZIP output stream.");
			String outFileName = inFileName + ".gz";
			GZIPOutputStream out = null;
			try {
				out = new GZIPOutputStream(new FileOutputStream(outFileName));
			} catch (FileNotFoundException e) {
				System.err.println("Could not create file: " + outFileName);
				System.exit(1);
			}
			System.out.println("Opening the input file.");
			FileInputStream in = null;
			try {
				in = new FileInputStream(inFileName);
			} catch (FileNotFoundException e) {
				System.err.println("File not found. " + inFileName);
				System.exit(1);
			}
			System.out
					.println("Transfering bytes from input file to GZIP Format.");
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			System.out.println("Completing the GZIP file");
			out.finish();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public static byte[] getBytesFromFile(String filePath) {
        if (filePath == null) {
            return null;
        }
        java.io.File file= new java.io.File(filePath);
        return getBytesFromFile(file);
    }
	
	public static byte[] getBytesFromFile(java.io.File dir) {
		if (dir == null) {
			return null;
		}

		try {
			FileInputStream stream = new FileInputStream(dir);
			return getBytesFromFile(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static byte[] getBytesFromFile(FileInputStream stream) {
        if (stream == null) {
            return null;
        }

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);

            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1) {
                out.write(b, 0, n);
            }
            stream.close();
            out.close();

            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

	public static void main(String[] args) {
		System.out.println(FileUtil.getClassPath(FileUtil.class));
	}
}

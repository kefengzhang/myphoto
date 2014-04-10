package  com.puduhe.util.security;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class HashUtil {

    private static final char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 获得流的hash码
     *
     * @param is
     * @param hashType
     *
     * @return
     *
     * @throws Exception
     */
    public static String getHashOfFile(InputStream is, String hashType)
            throws Exception {
        byte[] buffer = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        int numRead = 0;
        while ((numRead = is.read(buffer)) > 0) {
            md5.update(buffer, 0, numRead);
        }
        return toHexString(md5.digest());
    }

    /**
     * 获得文件的hash码
     *
     * @param fileName
     * @param hashType
     *
     * @return
     *
     * @throws Exception
     */
    public static String getHashOfFile(String fileName, String hashType)
            throws Exception {
        MessageDigest md5;
        InputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
            byte[] buffer = new byte[1024];
            md5 = MessageDigest.getInstance(hashType);
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return toHexString(md5.digest());
    }

    /**
     * 获得字符串的hash码
     *
     * @param inputString
     * @param hashType
     * @param charset
     *
     * @return
     *
     * @throws Exception
     */
    public static String getHashOfString(String inputString, String hashType,
                                         String charset) throws Exception {
        byte[] buffer = inputString.getBytes(charset);
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        int numRead = buffer.length;
        md5.update(buffer, 0, numRead);
        buffer = null;
        return toHexString(md5.digest());
    }

    /**
     * 将字符数组转换成字符串
     *
     * @param b
     *
     * @return
     */
    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }
}

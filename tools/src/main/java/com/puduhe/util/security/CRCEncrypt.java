package  com.puduhe.util.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;

public class CRCEncrypt {
    /**
     * 算出一个字附串的CRC值,并以大写的16进制返回
     *
     * @param s
     * @param charset
     *
     * @return
     */
    public static String crc(String s, String charset) {
        try {
            return crc(s.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 算出给定字节的CRC，并以大写的16进制返回
     *
     * @param bytes
     *
     * @return
     */
    public static String crc(byte[] bytes) {
        CRC32 crc = new CRC32();
        crc.update(bytes);
        return build(crc.getValue());
    }

    /**
     * 算出给定文件的CRC值,并以大写的16进制返回
     *
     * @param pathname
     *
     * @return
     *
     * @throws IOException
     */
    public static String crc(InputStream is) throws IOException {
        CRC32 crc = new CRC32();
        byte[] buf = new byte[1024];
        int readLen = 0;
        while ((readLen = is.read(buf, 0, 1024)) != -1) {
            crc.update(buf, 0, readLen);
        }
        return build(crc.getValue());
    }

    private static String build(long value) {
        return fillBit(Long.toHexString(value).toUpperCase(), 8);
    }

    /**
     * 按一定长度返回一个字附串，位数不够在前面补零
     *
     * @param s
     * @param mustLong
     *
     * @return
     */
    private static String fillBit(String s, int length) {
        StringBuilder sb = new StringBuilder();
        int strLong = s.length();
        for (int i = 0; i < length - strLong; i++) {
			sb.append("0");
		}
		sb.append(s);
		return sb.toString();
	}
}

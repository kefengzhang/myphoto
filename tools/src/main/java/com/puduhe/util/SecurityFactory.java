package  com.puduhe.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.puduhe.util.security.BASE64Encrypt;
import com.puduhe.util.security.CRCEncrypt;
import com.puduhe.util.security.DESEncrypt;
import com.puduhe.util.security.HashUtil;
import com.puduhe.util.security.RSAEncrypt;
import com.puduhe.util.security.SHA1Encrypt;

/** @author Administrator */
public class SecurityFactory {
    /*
      * BASE64工厂方法
      */
    /**
     * 对字节进行BASE64加密，默认UTF-8编码
     *
     * @param b
     *
     * @return
     */
    public static String BASE64Encode(byte[] b) {
        return BASE64Encode(b, "UTF-8");
    }

    /**
     * 对字节进行BASE64加密
     *
     * @param b
     * @param charset
     *
     * @return
     */
    public static String BASE64Encode(byte[] b, String charset) {
        return BASE64Encrypt.encode(b, "UTF-8");
    }

    /**
     * 对字符串BASE64加密，默认UTF-8编码
     *
     * @param s
     *
     * @return
     */
    public static String BASE64Encode(String s) {
        return BASE64Encode(s, "UTF-8");
    }

    /**
     * 对字符串BASE64加密
     *
     * @param s
     * @param charset
     *
     * @return
     */
    public static String BASE64Encode(String s, String charset) {
        return BASE64Encrypt.encode(s, charset);
    }

    /**
     * 对字节进行BASE64解密，默认UTF-8编码
     *
     * @param b
     *
     * @return
     */
    public static String BASE64Decode(byte[] b) {
        return BASE64Decode(b, "UTF-8");
    }

    /**
     * 对字节进行BASE64解密
     *
     * @param b
     * @param charset
     *
     * @return
     */
    public static String BASE64Decode(byte[] b, String charset) {
        return BASE64Encrypt.decode(b, charset);
    }

    /**
     * 对字符串进行BASE64解密，默认UTF-8编码
     *
     * @param s
     *
     * @return
     */
    public static String BASE64Decode(String s) {
        return BASE64Decode(s, "UTF-8");
    }

    /**
     * 对字符串进行BASE64解密
     *
     * @param s
     * @param charset
     *
     * @return
     */
    public static String BASE64Decode(String s, String charset) {
        return BASE64Encrypt.decode(s, charset);
    }

    /*
      * CRC工厂方法
      */
    /**
     * 获得字符串的CRC值，默认UTF-8编码
     *
     * @param s
     *
     * @return
     */
    public static String CRC(String s) {
        return CRC(s, "UTF-8");
    }

    /**
     * 获得字符串的CRC值
     *
     * @param s
     * @param charset
     *
     * @return
     */
    public static String CRC(String s, String charset) {
        return CRCEncrypt.crc(s, charset);
    }

    /**
     * 获得字节的CRC值
     *
     * @param bytes
     *
     * @return
     */
    public static String CRC(byte[] bytes) {
        return CRCEncrypt.crc(bytes);
    }

    /**
     * 获得流的CRC值
     *
     * @param is
     *
     * @return
     */
    public static String CRC(InputStream is) {
        try {
            return CRCEncrypt.crc(is);
        } catch (IOException e) {
            return null;
        }
    }

    /*
      * RSA工厂方法
      */
    /**
     * 对字符串进行RSA加密，默认UTF-8编码
     *
     * @param plaintext
     * @param modulus
     * @param publicExponent
     *
     * @return
     */
    public static String RSAEncode(String plaintext, String modulus,
                                   String publicExponent) {
        return RSAEncode(plaintext, modulus, publicExponent, "UTF-8");
    }

    /**
     * 对字符串进行RSA加密
     *
     * @param plaintext
     * @param modulus
     * @param publicExponent
     * @param charset
     *
     * @return
     */
    public static String RSAEncode(String plaintext, String modulus,
                                   String publicExponent, String charset) {
        try {
            return RSAEncrypt.encode(plaintext, modulus, publicExponent,
                    charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串进行RSA解密，默认UTF-8编码
     *
     * @param ciphertext
     * @param modulus
     * @param privateExponent
     *
     * @return
     */
    public static String RSADecode(String ciphertext, String modulus,
                                   String privateExponent) {
        return RSADecode(ciphertext, modulus, privateExponent, "UTF-8");
    }

    /**
     * 对字符串进行RSA解密
     *
     * @param ciphertext
     * @param modulus
     * @param privateExponent
     * @param charset
     *
     * @return
     */
    public static String RSADecode(String ciphertext, String modulus,
                                   String privateExponent, String charset) {
        try {
            return RSAEncrypt.decode(ciphertext, modulus, privateExponent,
                    charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
      * MD5工厂方法
      */
    /**
     * 对字符串进行MD5加密，默认UTF-8编码
     *
     * @param s
     *
     * @return
     */
    public static String MD5Encode(String s) {
        return MD5Encode(s, "UTF-8");
    }

    /**
     * 对字符串进行MD5加密
     *
     * @param s
     * @param charset
     *
     * @return
     */
    public static String MD5Encode(String s, String charset) {
        try {
            return HashUtil.getHashOfString(s, "md5", charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对流进行MD5加密
     *
     * @param is
     * @param charset
     *
     * @return
     */
    public static String MD5Encode(InputStream is) {
        try {
            return HashUtil.getHashOfFile(is, "MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字节进行SHA1加密
     *
     * @param bytes
     *
     * @return
     */
    public static byte[] SHA1Encode(byte[] bytes) {
        SHA1Encrypt sha = new SHA1Encrypt();
        try {
            return sha.getDigestOfBytes(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            sha = null;
        }
    }

    /**
     * 对字符串进行SHA1加密，默认UTF-8编码
     *
     * @param s
     *
     * @return
     */
    public static String SHA1Encode(String s) {
        return SHA1Encode(s, "UTF-8");
    }

    /**
     * 对字符串进行SHA1加密
     *
     * @param s
     * @param charset
     *
     * @return
     */
    public static String SHA1Encode(String s, String charset) {
        SHA1Encrypt sha = new SHA1Encrypt();
        try {
            return sha.getDigestOfString(s, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            sha = null;
        }
    }

    /*
      * DES工厂方法
      */
    /**
     * 对字符串进行DES加密，默认UTF-8编码
     *
     * @param s
     * @param key
     *
     * @return
     */
    @Deprecated //用不了,不要用这个方法,改用Des.java中的方法
    public static String DESEncode(String key, String s) {
        return DESEncode(key, s, "UTF-8");
    }

    /**
     * 对字符串进行DES加密
     *
     * @param s
     * @param key
     * @param charset
     *
     * @return
     */
    @Deprecated //用不了,不要用这个方法,改用Des.java中的方法
    public static String DESEncode(String key, String s, String charset) {
        try {
            return DESEncrypt.encode(key, s, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串进行DES解密，默认UTF-8编码
     *
     * @param s
     * @param key
     *
     * @return
     */
    @Deprecated //用不了,不要用这个方法,改用Des.java中的方法
	public static String DESDecode(String key, String s) {
		return DESDecode(key, s, "UTF-8");
	}

	/**
     * 对字符串进行DES解密
     *
     * @param s
     * @param key
     * @param charset
     *
     * @return
     */
    @Deprecated //用不了,不要用这个方法,改用Des.java中的方法
	public static String DESDecode(String key, String s, String charset) {
		try {
			return DESEncrypt.decode(key, s, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}

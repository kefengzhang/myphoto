package  com.puduhe.util.security;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/**
 * RSA 加解密
 *
 * @filename: com.kingsoft.commons.security/RSAEncrypt2.java
 * @author: LI Daobing
 * @time: Jan 6, 2009 10:09:19 AM
 */
public class RSAEncrypt2 {
    /**
     * RSA 加密
     *
     * @param pk  加密的公钥, 可用 <code>KeyPairGenerator.getInstance("RSA").generateKeyPair().getPublic()</code>获得
     * @param msg 需要加密的信息
     *
     * @return 加密后的信息
     *
     * @throws GeneralSecurityException
     */
    public static byte[] encode(PublicKey pk, byte[] msg) throws GeneralSecurityException {
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.ENCRYPT_MODE, pk);
        return c.doFinal(msg);
    }

    /**
     * RSA 解密
     *
     * @param pk  加密的私钥, 可用 <code>KeyPairGenerator.getInstance("RSA").generateKeyPair().getPrivate()</code>获得
     * @param msg 需要解密的信息
     *
     * @return 解密后的信息
     *
     * @throws GeneralSecurityException
     */
    public static byte[] decode(PrivateKey pk, byte[] msg) throws GeneralSecurityException {
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.DECRYPT_MODE, pk);

        int blocksize = c.getBlockSize();
        if (blocksize > 0) {
            for (int i = 0; i < msg.length; i += blocksize) {
                c.update(msg, i, blocksize);
            }
        } else {
			c.update(msg);
		}
		return c.doFinal();
	}
}

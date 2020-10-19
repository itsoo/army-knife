package com.cupshe.ak.text;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * <ol>
 * <li>MD5 加密</li>
 * <li>AES 加/解密</li>
 * </ol>
 *
 * @author zxy
 */
public class Encrypts {

    /*** 系统内部公用秘钥 */
    private final String rules;

    private static final String DEFAULT_RULES = "cupshe";

    public Encrypts() {
        this.rules = DEFAULT_RULES;
    }

    public Encrypts(String rules) {
        this.rules = rules;
    }

    /**
     * MD5 加密
     *
     * @param content 源内容
     * @return 加密内容
     */
    public String md5Encode(String content) {
        Objects.requireNonNull(content, "content cannot be null.");

        try {
            MessageDigest message = MessageDigest.getInstance("MD5");
            return String.format("%032x", new BigInteger(1, message.digest(content.getBytes(StandardCharsets.UTF_8))));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES 加密
     *
     * @param content 源内容
     * @return 加密内容
     */
    public String aesEncode(String content) {
        Objects.requireNonNull(content, "content cannot be null.");

        try {
            byte[] byteEncode = content.getBytes(StandardCharsets.UTF_8);
            byte[] byteAes = getCipher(Cipher.ENCRYPT_MODE).doFinal(byteEncode);
            return new BASE64Encoder().encode(byteAes);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES 解密
     *
     * @param content 源内容
     * @return 解密内容
     */
    public String aesDecode(String content) {
        Objects.requireNonNull(content, "content cannot be null.");

        try {
            byte[] byteContent = new BASE64Decoder().decodeBuffer(content);
            byte[] byteDecode = getCipher(Cipher.DECRYPT_MODE).doFinal(byteContent);
            return new String(byteDecode, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Cipher getCipher(int mode) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom((rules.getBytes(StandardCharsets.UTF_8))));
            SecretKey originalKey = keygen.generateKey();
            byte[] raw = originalKey.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(mode, key);
            return cipher;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
}

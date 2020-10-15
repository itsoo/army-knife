package com.cupshe.ak;

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
    public static final String DEFAULT_RULES = "n9FsDeFA7tB4xSRue0S1";

    /**
     * MD5 加密
     *
     * @param content 源内容
     * @return 加密内容
     */
    public static String md5Encode(String content) {
        Objects.requireNonNull(content, "<String>value must not be null");

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
     * @param rules   秘钥
     * @param content 源内容
     * @return 加密内容
     */
    public static String aesEncode(String rules, String content) {
        Objects.requireNonNull(content, "Encrypting content must not be null");

        try {
            byte[] byteEncode = content.getBytes(StandardCharsets.UTF_8);
            byte[] byteAes = getCipher(Cipher.ENCRYPT_MODE, rules).doFinal(byteEncode);
            return new BASE64Encoder().encode(byteAes);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES 解密
     *
     * @param rules   秘钥
     * @param content 源内容
     * @return 解密内容
     */
    public static String aesDecode(String rules, String content) {
        Objects.requireNonNull(content, "Encrypting content must not be null");

        try {
            byte[] byteContent = new BASE64Decoder().decodeBuffer(content);
            byte[] byteDecode = getCipher(Cipher.DECRYPT_MODE, rules).doFinal(byteContent);
            return new String(byteDecode, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Cipher getCipher(int mode, String rules) {
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

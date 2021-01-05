package com.cupshe.ak.text;

import lombok.SneakyThrows;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    private Encrypts(String rules) {
        this.rules = rules;
    }

    public static Encrypts of() {
        return of("CUPSHE.COM");
    }

    public static Encrypts of(String rules) {
        return new Encrypts(rules);
    }

    /**
     * MD5 加密
     *
     * @param content 源内容
     * @return 加密内容
     */
    @SneakyThrows
    public String md5Encode(String content) {
        Objects.requireNonNull(content, "content cannot be null.");
        String md5 = "MD5";
        MessageDigest message = MessageDigest.getInstance(md5);
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        return String.format("%032x", new BigInteger(1, message.digest(bytes)));
    }

    /**
     * AES 加密
     *
     * @param content 源内容
     * @return 加密内容
     */
    @SneakyThrows
    public String aesEncode(String content) {
        Objects.requireNonNull(content, "content cannot be null.");
        byte[] byteEncode = content.getBytes(StandardCharsets.UTF_8);
        byte[] byteAes = getCipher(Cipher.ENCRYPT_MODE).doFinal(byteEncode);
        return new BASE64Encoder().encode(byteAes);
    }

    /**
     * AES 解密
     *
     * @param content 源内容
     * @return 解密内容
     */
    @SneakyThrows
    public String aesDecode(String content) {
        Objects.requireNonNull(content, "content cannot be null.");
        byte[] byteContent = new BASE64Decoder().decodeBuffer(content);
        byte[] byteDecode = getCipher(Cipher.DECRYPT_MODE).doFinal(byteContent);
        return new String(byteDecode, StandardCharsets.UTF_8);
    }

    private Cipher getCipher(int mode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        String aes = "AES";
        KeyGenerator kg = KeyGenerator.getInstance(aes);
        kg.init(128, new SecureRandom((rules.getBytes(StandardCharsets.UTF_8))));
        Cipher cipher = Cipher.getInstance(aes);
        cipher.init(mode, new SecretKeySpec(kg.generateKey().getEncoded(), aes));
        return cipher;
    }
}

package com.cupshe.ak.text;

import lombok.SneakyThrows;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * <ol>
 *   <li>MD5 加密</li>
 *   <li>AES 加/解密</li>
 * </ol>
 *
 * @author zxy
 */
public class Encrypts {

    private final SecureRandom secureRandom;

    private final SecretKey secretKey;

    private Encrypts() {
        secureRandom = null;
        secretKey = null;
    }

    private Encrypts(String rules) {
        secureRandom = new SecureRandom(rules.getBytes(StandardCharsets.UTF_8));
        secretKey = new SecretKeySpec(getSecretKey().getEncoded(), "AES");
    }

    public static Encrypts of() {
        return new Encrypts();
    }

    public static Encrypts of(String rules) {
        Objects.requireNonNull(rules, "rules cannot be null.");
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
        MessageDigest message = MessageDigest.getInstance("MD5");
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
        Objects.requireNonNull(secureRandom, "rules have not been set yet.");
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
        Objects.requireNonNull(secureRandom, "rules have not been set yet.");
        byte[] byteContent = new BASE64Decoder().decodeBuffer(content);
        byte[] byteDecode = getCipher(Cipher.DECRYPT_MODE).doFinal(byteContent);
        return new String(byteDecode, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    private SecretKey getSecretKey() {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128, secureRandom);
        return kg.generateKey();
    }

    @SneakyThrows
    private Cipher getCipher(int mode) {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(mode, secretKey);
        return cipher;
    }
}

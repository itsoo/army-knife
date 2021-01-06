package com.cupshe.ak.text;

import org.junit.Assert;
import org.junit.Test;

/**
 * EncryptsTests
 *
 * @author zxy
 */
public class EncryptsTests {

    private final Encrypts encrypts = Encrypts.of("test");

    @Test
    public void testAes() {
        String source = "zhang san";
        String encode = encrypts.aesEncode(source);
        String decode = encrypts.aesDecode(encode);
        Assert.assertEquals("AES", source, decode);
    }

    @Test
    public void testMd5() {
        String str = encrypts.md5Encode("zhang san");
        Assert.assertEquals("MD5", str, "2b8edc155d959faa2aba565fd22251c0");
    }
}

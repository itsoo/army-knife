package com.cupshe.ak.net;

import org.junit.Test;

/**
 * UuidUtilsTests
 *
 * @author zxy
 */
public class UuidUtilsTests {

    @Test
    public void test() {
        String uuid = UuidUtils.createUuid();
        System.out.println(uuid);
    }
}

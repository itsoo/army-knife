package com.cupshe.ak.net;

import org.junit.Test;

/**
 * UriUtilsTests
 *
 * @author zxy
 */
public class UriUtilsTests {

    @Test
    public void test() {
        String name = UriUtils.encode("zhang san");
        String age = UriUtils.encode("18");
        System.out.println("name=" + name + "&age=" + age);
    }
}

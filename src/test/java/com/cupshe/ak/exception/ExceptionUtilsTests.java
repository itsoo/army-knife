package com.cupshe.ak.exception;

import org.junit.Test;

/**
 * ExceptionUtilsTests
 *
 * @author zxy
 */
public class ExceptionUtilsTests {

    @Test
    public void test() {
        String msg = ExceptionUtils.getStackTrace(new BusinessException("000001", "TEST MESSAGE"));
        System.out.println(msg);
    }
}

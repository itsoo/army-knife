package com.cupshe.ak.objects;

import org.junit.Test;

import java.util.Arrays;

/**
 * ArrayUtilsTests
 *
 * @author zxy
 */
public class ArrayUtilsTests {

    @Test
    public void testMerge() {
        String[] s1 = {"-1-", "-2-"};
        String[] s2 = {"-3-"};
        String[] s3 = {};
        String[] s4 = {"-4-", "-5-", "-6-"};
        System.out.println(Arrays.toString(ArrayUtils.merge(s1, s2, s3, s4, null)));
        System.out.println(Arrays.toString(ArrayUtils.merge(null, s1, s2, s3)));
        System.out.println(Arrays.toString(ArrayUtils.merge(null, s1, s2, null, s3)));
    }

    @Test
    public void testConcat() {
        String[] s1 = {"-1-"};
        String s2 = "-2-";
        String s3 = "-3-";
        String s4 = "-4-";
        System.out.println(Arrays.toString(ArrayUtils.concat(s1, s2, s3, s4, null)));
        System.out.println(Arrays.toString(ArrayUtils.concat(null, s1, s2, s3)));
        System.out.println(Arrays.toString(ArrayUtils.concat(null, s1, s2, null, s3)));
    }
}

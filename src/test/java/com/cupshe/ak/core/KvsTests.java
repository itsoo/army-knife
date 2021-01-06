package com.cupshe.ak.core;

import org.junit.Test;

/**
 * KvsTests
 *
 * @author zxy
 */
public class KvsTests {

    @Test
    public void test() {
        Kvs kvs = new Kvs();
        kvs.add(new Kv("zhangsan", 18));
        kvs.add(new Kv("lisi", 19));
        System.out.println(kvs);
    }
}

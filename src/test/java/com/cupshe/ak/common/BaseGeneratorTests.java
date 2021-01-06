package com.cupshe.ak.common;

import org.junit.Test;

import static com.cupshe.ak.common.BaseGenerator.PrimaryKeyGenerator;
import static com.cupshe.ak.common.BaseGenerator.RandomLetterGenerator;

/**
 * BaseGeneratorTests
 *
 * @author zxy
 */
public class BaseGeneratorTests {

    // PrimaryKeyGenerator.of(<数据中心编号>) ==> 从网卡自动计算「机器编号」
    // PrimaryKeyGenerator.of(<数据中心编号>, <机器编号>)
    private final PrimaryKeyGenerator pkg = PrimaryKeyGenerator.of(1L, 1);

    // RandomLetterGenerator.of(<随机字符串长度>)
    private final RandomLetterGenerator rlg = RandomLetterGenerator.of(6);

    @Test
    public void testGenericPrimaryKey() {
        System.out.println(pkg.next());
    }

    @Test
    public void testGenericRandomLetter() {
        System.out.println(rlg.next());
    }
}

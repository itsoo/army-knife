package com.cupshe.ak.text;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * TrieFilterTests
 *
 * @author zxy
 */
public class TrieFilterTests {

    private final List<String> source = new LinkedList<String>() {{
        add("abc");
        add("Acd");
        add("aB");
        add("bc");
        add("AA");
        add("Fe");
        add("eee");
    }};

    @Test
    public void test() {
        TrieFilter filter = TrieFilter.create(source);
        System.out.println("abc: " + filter.mightContain("ff91 ff91 huo huo ** liSsi: abc ==> 3306"));
        System.out.println(" ab: " + filter.mightContain("ff91 ff91 huo huo ** liSsi: ab ==> 3306"));
        System.out.println(" ac: " + filter.mightContain("ff91 ff91 huo huo ** liSsi: ac ==> 3306"));
        System.out.println("acd: " + filter.mightContain("ff91 ff91 huo huo ** liSsi: acd ==> 3306"));
        System.out.println("ace: " + filter.mightContain("ff91 ff91 huo huo ** liSsi: ace ==> 3306"));
        System.out.println(" bc: " + filter.mightContain("ff91 ff91 huo huo ** liSsi: bc ==> 3306"));
        System.out.println(" bb: " + filter.mightContain("ff91 ff91 huo huo ** liSsi: bb ==> 3306"));
        System.out.println(" aa: " + filter.mightContain("ff91 ff91 huo huo ** liSsi: aa ==> 3306"));
        System.out.println(" ee: " + filter.mightContain("ff91 ff91 huo huo ** liSsi: ee ==> 3306"));
        System.out.println("eEe: " + filter.mightContain("ff91 ff91 huo huo ** liSsi: eEe ==> 3306"));
    }
}

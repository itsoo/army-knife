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
        System.out.println("abc: " + filter.mightContain("zxy*liSsi-abc:3306"));
        System.out.println(" ab: " + filter.mightContain("zxy*liSsi-ab:3306"));
        System.out.println(" ac: " + filter.mightContain("zxy*liSsi-ac:3306"));
        System.out.println("acd: " + filter.mightContain("zxy*liSsi-acd:3306"));
        System.out.println("ace: " + filter.mightContain("zxy*liSsi-ace:3306"));
        System.out.println(" bc: " + filter.mightContain("zxy*liSsi-bc:3306"));
        System.out.println(" bb: " + filter.mightContain("zxy*liSsi-bb:3306"));
        System.out.println(" aa: " + filter.mightContain("zxy*liSsi-aa:3306"));
        System.out.println(" ee: " + filter.mightContain("zxy*liSsi-ee:3306"));
        System.out.println("eEe: " + filter.mightContain("zxy*liSsi-eEe:3306"));
    }
}

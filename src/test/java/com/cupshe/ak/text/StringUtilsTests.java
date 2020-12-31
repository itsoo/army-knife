package com.cupshe.ak.text;

import org.junit.Test;

/**
 * StringUtilsTests
 *
 * @author zxy
 */
public class StringUtilsTests {

    @Test
    public void testTrimLeadingCharacter() {
        System.out.println(StringUtils.trimLeadingCharacter("", 's'));
        System.out.println(StringUtils.trimLeadingCharacter("s", 's'));
        System.out.println(StringUtils.trimLeadingCharacter("ss", 's'));
        System.out.println(StringUtils.trimLeadingCharacter("ssa", 's'));
        System.out.println(StringUtils.trimLeadingCharacter("ssas", 's'));
        System.out.println(StringUtils.trimLeadingCharacter("ssass", 's'));

        System.out.println(StringUtils.trimLeadingCharacter("", ' '));
        System.out.println(StringUtils.trimLeadingCharacter(" ", ' '));
        System.out.println(StringUtils.trimLeadingCharacter("  ", ' '));
        System.out.println(StringUtils.trimLeadingCharacter("s ", ' '));
        System.out.println(StringUtils.trimLeadingCharacter(" s  ", ' '));
        System.out.println(StringUtils.trimLeadingCharacter("  s   ", ' '));
    }

    @Test
    public void testTrimTrailingCharacter() {
        System.out.println(StringUtils.trimTrailingCharacter("", 'd'));
        System.out.println(StringUtils.trimTrailingCharacter("d", 'd'));
        System.out.println(StringUtils.trimTrailingCharacter("dd", 'd'));
        System.out.println(StringUtils.trimTrailingCharacter("add", 'd'));
        System.out.println(StringUtils.trimTrailingCharacter("dadd", 'd'));
        System.out.println(StringUtils.trimTrailingCharacter("ddadd", 'd'));

        System.out.println(StringUtils.trimTrailingCharacter("", ' '));
        System.out.println(StringUtils.trimTrailingCharacter(" ", ' '));
        System.out.println(StringUtils.trimTrailingCharacter("  ", ' '));
        System.out.println(StringUtils.trimTrailingCharacter("d ", ' '));
        System.out.println(StringUtils.trimTrailingCharacter(" d  ", ' '));
        System.out.println(StringUtils.trimTrailingCharacter("  d   ", ' '));
    }
}

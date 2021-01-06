package com.cupshe.ak.text;

import org.junit.Test;

/**
 * HighlightRendersTests
 *
 * @author zxy
 */
public class HighlightRendersTests {

    @Test
    public void testHighlight() {
        String html = HighlightRenders.render(
                HighlightRenders.SQL_HIGHLIGHT_RENDER,
                "SELECT *\n" +
                        "  FROM test_table\n" +
                        " where name = '''abc'\n" +
                        "   having count(*) > 1");
        System.out.println(html);
    }

    @Test
    public void testLineNumber() {
        String html = HighlightRenders.render(
                HighlightRenders.SHOW_LINE_NUMBER_RENDER,
                "SELECT *\n" +
                        "  FROM test_table\n" +
                        " where name = '''abc'\n" +
                        "   having count(*) > 1");
        System.out.println(html);
    }

    @Test
    public void testHighlightWithLineNumber() {
        String html = HighlightRenders.render(
                HighlightRenders.SQL_HIGHLIGHT_RENDER,
                "SELECT *\n" +
                        "  FROM test_table\n" +
                        " where name = '''abc'\n" +
                        "   having count(*) > 1");
        html = HighlightRenders.render(
                HighlightRenders.SHOW_LINE_NUMBER_RENDER, html);
        System.out.println(html);
    }
}

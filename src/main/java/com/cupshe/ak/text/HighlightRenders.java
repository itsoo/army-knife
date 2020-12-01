package com.cupshe.ak.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 高亮渲染工具类
 *
 * @author zxy
 */
public class HighlightRenders {

    /*** SQL 高亮渲染器 */
    public static final HighlightRender SQL_HIGHLIGHT_RENDER = new SqlHighlightRender();

    /*** 显示行号渲染器 */
    public static final HighlightRender SHOW_LINE_NUMBER_RENDER = new ShowLineNumberRender();

    /**
     * 高亮渲染
     *
     * @param hr      高亮渲染器
     * @param content 目标内容
     * @return 结果内容
     */
    public static String render(HighlightRender hr, String content) {
        return hr.doRender(content);
    }

    private static class ShowLineNumberRender implements HighlightRender {

        private static final String FONT_STYLE = "font-size: inherit; font-family: inherit; line-height: inherit;";

        @Override
        public String doRender(String content) {
            StringBuilder result = new StringBuilder();
            String[] splits = content.split("\n");
            String template = "<table style=\"%s border-collapse: collapse; border-spacing: 0;\">";
            result.append(String.format(template, FONT_STYLE));
            for (int i = 0; i < splits.length; i++) {
                result.append("<tr>");
                appendLineNumb(result, i + 1);
                appendCode(result, splits[i]);
                result.append("</tr>");
            }

            return result.append("</table>").toString();
        }

        private void appendLineNumb(StringBuilder sb, int number) {
            sb.append("<td style=\"")
                    .append(FONT_STYLE)
                    .append("-ms-user-select: none; -webkit-user-select: none; -moz-user-select: none;")
                    .append("user-select: none; color: #babbbd; text-align: right; padding: 0 10px;\">")
                    .append(number)
                    .append("</td>");
        }

        private void appendCode(StringBuilder sb, String code) {
            sb.append("<td style=\"")
                    .append(FONT_STYLE)
                    .append("word-wrap: normal; white-space: pre; overflow: visible;")
                    .append("position: relative; vertical-align: top;\">")
                    .append(code)
                    .append("</td>");
        }
    }

    private static class SqlHighlightRender implements HighlightRender {

        private static final Pattern PATTERN = Pattern.compile("(?msi)('(?:''|[^'])*')|(\"(?:\"\"|[^\"])*\")" +
                "|--.*?$|/\\*.*?\\*/|\\b(ADD|ALL|ALTER|ANALYZE|AND|AS|ASC|ASENSITIVE|BEFORE|BETWEEN|BIGINT" +
                "|BINARY|BLOB|BOTH|BY|CALL|CASCADE|CASE|CHANGE|CHAR|CHARACTER|CHECK|COLLATE|COLUMN|CONDITION" +
                "|CONNECTION|CONSTRAINT|CONTINUE|CONVERT|CREATE|CROSS|CURRENT_DATE|CURRENT_TIME|CURRENT_TIMESTAMP" +
                "|CURRENT_USER|CURSOR|DATABASE|DATABASES|DAY_HOUR|DAY_MICROSECOND|DAY_MINUTE|DAY_SECOND|DEC" +
                "|DECIMAL|DECLARE|DEFAULT|DELAYED|DELETE|DESC|DESCRIBE|DETERMINISTIC|DISTINCT|DISTINCTROW|DIV" +
                "|DOUBLE|DROP|DUAL|EACH|ELSE|ELSEIF|ENCLOSED|ESCAPED|EXISTS|EXIT|EXPLAIN|FALSE|FETCH|FLOAT|FLOAT4" +
                "|FLOAT8|FOR|FORCE|FOREIGN|FROM|FULLTEXT|GOTO|GRANT|GROUP|HAVING|HIGH_PRIORITY|HOUR_MICROSECOND" +
                "|HOUR_MINUTE|HOUR_SECOND|IF|IGNORE|IN|INDEX|INFILE|INNER|INOUT|INSENSITIVE|INSERT|INT|INT1|INT2" +
                "|INT3|INT4|INT8|INTEGER|INTERVAL|INTO|IS|ITERATE|JOIN|KEY|KEYS|KILL|LABEL|LEADING|LEAVE|LEFT|LIKE" +
                "|LIMIT|LINEAR|LINES|LOAD|LOCALTIME|LOCALTIMESTAMP|LOCK|LONG|LONGBLOB|LONGTEXT|LOOP|LOW_PRIORITY" +
                "|MATCH|MEDIUMBLOB|MEDIUMINT|MEDIUMTEXT|MIDDLEINT|MINUTE_MICROSECOND|MINUTE_SECOND|MOD|MODIFIES" +
                "|NATURAL|NOT|NO_WRITE_TO_BINLOG|NULL|NUMERIC|ON|OPTIMIZE|OPTION|OPTIONALLY|OR|ORDER|OUT|OUTER" +
                "|OUTFILE|PRECISION|PRIMARY|PROCEDURE|PURGE|RAID0|RANGE|READ|READS|REAL|REFERENCES|REGEXP|RELEASE" +
                "|RENAME|REPEAT|REPLACE|REQUIRE|RESTRICT|RETURN|REVOKE|RIGHT|RLIKE|SCHEMA|SCHEMAS|SECOND_MICROSECOND" +
                "|SELECT|SENSITIVE|SEPARATOR|SET|SHOW|SMALLINT|SPATIAL|SPECIFIC|SQL|SQLEXCEPTION|SQLSTATE|SQLWARNING" +
                "|SQL_BIG_RESULT|SQL_CALC_FOUND_ROWS|SQL_SMALL_RESULT|SSL|STARTING|STRAIGHT_JOIN|TABLE|TERMINATED" +
                "|THEN|TINYBLOB|TINYINT|TINYTEXT|TO|TRAILING|TRIGGER|TRUE|UNDO|UNION|UNIQUE|UNLOCK|UNSIGNED|UPDATE" +
                "|USAGE|USE|USING|UTC_DATE|UTC_TIME|UTC_TIMESTAMP|VALUES|VARBINARY|VARCHAR|VARCHARACTER|VARYING" +
                "|WHEN|WHERE|WHILE|WITH|WRITE|X509|XOR|YEAR_MONTH|ZEROFILL)\\b");

        @Override
        public String doRender(String content) {
            StringBuilder result = new StringBuilder();
            Matcher m = PATTERN.matcher(content);
            int i = 0;
            for (String c; m.find(); ) {
                c = m.group();
                result.append(content, i, (i = m.start()));
                if (isValuesContent(c)) {
                    renderHighlightValues(result, c);
                } else if (isCommentsContent(c)) {
                    renderHighlightComments(result, c);
                } else {
                    renderHighlightKeywords(result, c);
                }

                i += c.length();
            }

            return result.append(content.substring(i)).toString();
        }

        private boolean isValuesContent(String c) {
            return c.startsWith("'") || c.startsWith("\"");
        }

        private boolean isCommentsContent(String c) {
            return c.startsWith("--") || c.startsWith("/*");
        }

        private void renderHighlightValues(StringBuilder sb, String c) {
            sb.append("<span style=\"color: #080; font-weight: bold;\">").append(c).append("</span>");
        }

        private void renderHighlightComments(StringBuilder sb, String c) {
            sb.append("<span style=\"color: #888; font-style: normal;\">").append(c).append("</span>");
        }

        private void renderHighlightKeywords(StringBuilder sb, String c) {
            sb.append("<span style=\"color: #04e; font-weight: bold;\">").append(c).append("</span>");
        }
    }

    /**
     * 高亮渲染接口留作扩展用
     */
    private interface HighlightRender {
        /**
         * doRender
         *
         * @param content origin content
         * @return content with HTML
         */
        String doRender(String content);
    }
}

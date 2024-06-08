package com.power.common.util;

/**
 * StringUtil
 * @javadoc
 * @author sunyu
 */

import com.power.common.constants.Charset;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static final String EMPTY = "";
    private static final String SERIAL_NO_PATTERN = "yyyyMMddHHmmssSSS";
    private static final char UNDERLINE = '_';
    private static final char HYPHEN_LINE = '-';

    /**
     * Checks if a CharSequence is empty or null.
     *
     * @param str String
     * @return {@code true} if the CharSequence is empty or null
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim()) || "null".equals(str.trim()) || "NaN".equals(str.trim());
    }

    /**
     * Checks if a CharSequence is empty or null.
     *
     * @param str String
     * @return {@code false } if the CharSequence is empty or null
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Check if characters are the same in the string.
     *
     * @param s the CharSequence to check.
     * @return {@code true } if characters are the same in the string
     */
    public static boolean isSameCharacter(String s) {
        s = s.toUpperCase();
        String character = s.substring(0, 1);
        String replace = "";
        String test = s.replace(character, replace);
        return EMPTY.equals(test);
    }

    /**
     * Check if the character in a string is a continuous character
     *
     * @param s the CharSequence to check
     * @return {@code true } if the character in a string is a continuous character
     */
    public static boolean isContinuityCharacter(String s) {
        boolean continuity = true;
        char[] data = s.toCharArray();
        for (int i = 0; i < data.length - 1; i++) {
            int a = Integer.parseInt(data[i] + "");
            int b = Integer.parseInt(data[i + 1] + "");
            continuity = continuity && (a + 1 == b || a - 1 == b);
        }
        return continuity;
    }

    /**
     * get char code
     *
     * @param str String
     * @return String
     */
    public static String getCharCode(String str) {
        String temp = "";
        for (int i = 0; i < temp.length(); i++) {
            temp += Integer.toHexString(str.charAt(i)) + "nbsp;";
        }
        return temp;
    }

    /**
     * convert to 8859
     *
     * @param str String
     * @return String
     */
    public static String convertTo8859(String str) {
        String strOutPut = "";
        try {
            byte[] tempStrByte = str.getBytes(StandardCharsets.ISO_8859_1);
            strOutPut = new String(tempStrByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strOutPut;
    }

    public static String capitalise(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * Remove string space
     *
     * @param sourceStr input string
     * @return string
     */
    public static String trim(String sourceStr) {
        if (isEmpty(sourceStr)) {
            return null;
        } else {
            return sourceStr.replaceAll(" ", "");
        }
    }

    /**
     * Clear special characters in a string
     *
     * @param str String
     * @return String
     */
    public static String filterStr(String str) {
        if (!isEmpty(str)) {
            str = str.replaceAll(";", "");
            str = str.replaceAll("%", "");
            str = str.replaceAll("--", "");
            str = str.replaceAll("/", "");
            str = str.replaceAll("=", "");
            str = str.replaceAll("'", "&#39;");
            str = str.replaceAll("\\(", "&#40;").replace("\\)", "&#41;");
            str = str.replaceAll("<", "&lt");
            str = str.replaceAll(">", "&gt");
        }
        return str;
    }

    /**
     * Clear wildcards in sql
     *
     * @param str sql
     * @return string
     */
    public static String cleanSqlWildCharacter(String str) {
        if (!isEmpty(str)) {
            str = str.replaceAll("%", "invalid character");
            str = str.replaceAll("_", "invalid character");
            str = str.replaceAll("=", "invalid character");
        }
        return str;
    }

    /**
     * Clear xss script injection
     *
     * @param value script
     * @return not contains xss script
     */
    public static String cleanXSS(String value) {
        if (null == value) {
            return null;
        } else {
            value = value.replaceAll("\\bselect\\b", "invalid character");
            value = value.replaceAll("\\band\\b", "invalid character");
            value = value.replaceAll("\\bor\\b", "invalid character");
            value = value.replaceAll("\\bdelete\\b", "invalid character");
            value = value.replaceAll("\\bjoin\\b", "invalid character");
            value = value.replaceAll("\\bdrop\\b", "invalid character");

            value = value.replaceAll("\\+", "&#43;");
            value = value.replaceAll("&", "&amp;");
            value = value.replaceAll("%", "&#37;");
            // value = value.replaceAll("\"","&quot;");
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            value = value.replaceAll("%3C", "&lt;").replaceAll("%3E", "&gt;");
            value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
            value = value.replaceAll("%28", "&#40;").replaceAll("%29", "&#41;");
            value = value.replaceAll("'", "&#39;");
            value = value.replaceAll("alert", "invalid character");
            value = value.replaceAll("eval\\((.*)\\)", "invalid character");
            value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
            value = value.replaceAll("<\\s*script", "invalid character");
            value = value.replaceAll("location.href", "invalid character");
        }
        return value;
    }

    /**
     * camel to underline
     *
     * @param param pending character
     * @return String
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return EMPTY;
        }
        int length = param.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * underline to camel
     *
     * @param param pending character
     * @return String
     */
    public static String underlineToCamel(String param) {
        return toCamel(param, UNDERLINE);
    }

    /**
     * hyphen line to camel
     *
     * @param param pending character
     * @return String
     */
    public static String hyphenLineToCamel(String param) {
        return toCamel(param, HYPHEN_LINE);
    }

    /**
     * Camel case
     *
     * @param s characters
     * @return String after Camel case
     */
    public static String toCapitalizeCamelCase(String s) {
        if (Objects.isNull(s)) {
            return null;
        }
        s = underlineToCamel(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * Turn the first letter into a uppercase
     *
     * @param param pending character
     * @return String
     */
    public static String firstToUpperCase(String param) {
        char[] cs = param.toCharArray();
        if (cs[0] > 96 && cs[0] < 123) {
            cs[0] -= 32;
        }
        return String.valueOf(cs);
    }

    /**
     * Turn the first letter into a lowercase
     *
     * @param param pending character
     * @return String
     */
    public static String firstToLowerCase(String param) {
        char[] cs = param.toCharArray();
        if (cs[0] > 64 && cs[0] < 91) {
            cs[0] += 32;
        }
        return String.valueOf(cs);
    }

    /**
     * Generating sequence number according to timestamp
     *
     * @return String
     */
    public static String createSerialNo() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(SERIAL_NO_PATTERN);
        return format.format(cal.getTime());
    }

    /**
     * Decoding the parameters passed by the URL
     *
     * @param str pending character
     * @return String after decode
     */
    public static String urlDecode(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            try {
                return java.net.URLDecoder.decode(str, Charset.DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Encoding the parameters for URL
     *
     * @param str pending character
     * @return String after encode
     */
    public static String urlEncode(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        } else {
            try {
                return java.net.URLEncoder.encode(str, Charset.DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * convert 8859 to utf-8
     *
     * @param str pending character
     * @return String
     */
    public static String ios8859ToUtf8(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            try {
                return new String(str.getBytes("iso8859-1"), Charset.DEFAULT_CHARSET);
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**
     * Convert binary strings to hexadecimal strings
     *
     * @param bString binary string
     * @return String
     */
    public static String binaryString2hexString(String bString) {
        if (isEmpty(bString) || bString.length() % 8 != 0) {
            return null;
        }
        StringBuilder tmp = new StringBuilder();
        int iTmp;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    /**
     * Convert hex strings to binary strings
     *
     * @param hexString hexadecimal strings
     * @return binary strings
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        StringBuilder bString = new StringBuilder();
        String tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString.append(tmp.substring(tmp.length() - 4));
        }
        return bString.toString();
    }

    /**
     * Usage: StringUtil.format("key={},value={}", "1","2")
     * @param format the format string
     * @param args a list of arguments
     * @return formatted string
     */
    public static String format(String format, Object ...args) {
        if (Objects.isNull(format)) {
            return null;
        }
        int i = 0;
        while(format.contains("{}")) {
            format = format.replaceFirst(Pattern.quote("{}"), "{"+ i++ +"}");
        }
        return MessageFormat.format(format, args);
    }

    /**
     * left trim and right trim
     *
     * @param str string
     * @return string
     */
    public static String trimBlank(String str) {
        if (isEmpty(str)) {
            return null;
        } else {
            return str.replaceAll("^[　 ]+|[　 ]+$", "");
        }
    }

    public static int length(String str) {
        if (isEmpty(str)) {
            return 0;
        } else {
            return str.length();
        }
    }

    /**
     * Remove single or double quotes in query keywords to avoid sql errors
     *
     * @param str String
     * @return String
     */
    public static String removeQuotes(String str) {
        if (isNotEmpty(str)) {
            return str.replaceAll("'", EMPTY).replaceAll("\"", EMPTY);
        } else {
            return EMPTY;
        }
    }

    /**
     * Remove double quotes
     *
     * @param str String
     * @return String
     */

    public static String removeDoubleQuotes(String str) {
        if (isNotEmpty(str)) {
            return str.replaceAll("\"", EMPTY);
        } else {
            return EMPTY;
        }
    }


    /**
     * Extract Chinese in a string
     *
     * @param str characters
     * @return Chinese characters
     */
    public static String getChinese(String str) {
        String reg = "[^\u4e00-\u9fa5]";
        str = str.replaceAll(reg, EMPTY);
        return str;
    }

    /**
     * Extract non-Chinese characters in a string
     *
     * @param str characters
     * @return non-Chinese characters
     */
    public static String getNotChinese(String str) {
        String reg = "[^A-Za-z0-9_]";
        str = str.replaceAll(reg, EMPTY);
        return str;
    }

    /**
     * Remove the specified prefix
     *
     * @param str    source
     * @param prefix prefix
     * @return If the prefix does not match, return the original string
     */
    public static String removePrefix(String str, String prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return str;
        }
        if (str.startsWith(prefix)) {
            return str.substring(prefix.length());
        }
        return str;
    }

    /**
     * split
     *
     * @param str   source str
     * @param regex regex expression
     * @return array of String
     */
    public static String[] split(String str, String regex) {
        if (Objects.isNull(str)) {
            return null;
        }
        return str.split(regex);
    }

    /**
     * Left pad a long number with zero
     *
     * @param seq sequence number
     * @param len max length of number
     * @return String
     */
    public static String seqNumLeftPadZero(long seq, int len) {
        String b = String.valueOf(seq);
        StringBuilder builder = new StringBuilder();
        int rest = len - b.length();
        for (int i = 0; i < rest; i++) {
            builder.append("0");
        }
        builder.append(b);
        return builder.toString();
    }

    /**
     * convert unicode to string
     *
     * @param unicode unicode character
     * @return String
     */
    public static String unicode2String(String unicode) {
        if (StringUtil.isEmpty(unicode)) {
            return EMPTY;
        }
        StringBuilder string = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            string.append((char) data);
        }
        return string.toString();
    }

    private static String toCamel(String param, char s) {
        if (param == null || "".equals(param.trim())) {
            return EMPTY;
        }
        int length = param.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = param.charAt(i);
            if (c == s) {
                if (++i < length) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Tokenize the given String into a String array via a StringTokenizer.
     * Trims tokens and omits empty tokens.
     * <p>The given delimiters string is supposed to consist of any number of
     * delimiter characters. Each of those characters can be used to separate
     * tokens. A delimiter is always a single character; for multi-character
     * delimiters, consider using <code>delimitedListToStringArray</code>
     *
     * <p>Copied from the Spring Framework while retaining all license, copyright and author information.
     *
     * @param str        the String to tokenize
     * @param delimiters the delimiter characters, assembled as String
     *                   (each of those characters is individually considered as delimiter).
     * @return an array of the tokens
     * @see java.util.StringTokenizer
     * @see java.lang.String#trim()
     */
    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, Boolean.TRUE, Boolean.TRUE);
    }

    /**
     * Tokenize the given String into a String array via a StringTokenizer.
     * <p>The given delimiters string is supposed to consist of any number of
     * delimiter characters. Each of those characters can be used to separate
     * tokens. A delimiter is always a single character; for multi-character
     * delimiters, consider using <code>delimitedListToStringArray</code>
     *
     * <p>Copied from the Spring Framework while retaining all license, copyright and author information.
     *
     * @param str               the String to tokenize
     * @param delimiters        the delimiter characters, assembled as String
     *                          (each of those characters is individually considered as delimiter)
     * @param trimTokens        trim the tokens via String's <code>trim</code>
     * @param ignoreEmptyTokens omit empty tokens from the result array
     *                          (only applies to tokens that are empty after trimming; StringTokenizer
     *                          will not consider subsequent delimiters as token in the first place).
     * @return an array of the tokens (<code>null</code> if the input String
     * was <code>null</code>)
     * @see java.util.StringTokenizer
     * @see java.lang.String#trim()
     */
    @SuppressWarnings({"unchecked"})
    public static String[] tokenizeToStringArray(
            String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

        if (str == null) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }

    /**
     * Copy the given Collection into a String array.
     * The Collection must contain String elements only.
     *
     * <p>Copied from the Spring Framework while retaining all license, copyright and author information.
     *
     * @param collection the Collection to copy
     * @return the String array (<code>null</code> if the passed-in
     * Collection was <code>null</code>)
     */
    @SuppressWarnings({"unchecked"})
    public static String[] toStringArray(Collection collection) {
        if (Objects.isNull(collection)) {
            return null;
        }
        return (String[]) collection.toArray(new String[0]);
    }


}

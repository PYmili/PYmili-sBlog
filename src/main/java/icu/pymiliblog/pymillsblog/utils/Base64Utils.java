package icu.pymiliblog.pymillsblog.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64的工具类
 * @author PYmili
 */
public class Base64Utils {
    /**
     * 解码
     * @param str {@link String}
     * @return {@link String}
     */
    public static String decode(String str) {
        if (str == null || str.length() <= 0) return str;
        byte[] base64Bytes = Base64.getDecoder().decode(str);
        // byte[] 转 String（解码后的字符串）
        return new String(base64Bytes, StandardCharsets.UTF_8);
    }

    /**
     * 编码
     * @param str {@link String}
     * @return {@link String}
     */
    public static String encode(String str) {
        if (str == null || str.length() <= 0) return str;
        byte[] base64Bytes = Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8));
        return new String(base64Bytes, StandardCharsets.UTF_8);
    }
}

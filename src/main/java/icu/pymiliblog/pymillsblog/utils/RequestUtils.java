package icu.pymiliblog.pymillsblog.utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 网络请求工具类
 * @author PYmili
 */
public class RequestUtils {

    /**
     * 获取发送请求的人的地址
     * @param request {@link HttpServletRequest}
     * @return {@link String}
     */
    public static String getRemoteAddress(HttpServletRequest request) {
        String address = request.getHeader("X-Forwarded-For");
        if (address != null && !address.isEmpty() && !"unknown".equalsIgnoreCase(address)) {
            // 多个IP地址时取第一个
            address = address.split(",")[0];
        } else {
            address = request.getHeader("Proxy-Client-IP");
            if (address == null || address.isEmpty() || "unknown".equalsIgnoreCase(address)) {
                address = request.getHeader("WL-Proxy-Client-IP");
            }
            if (address == null || address.isEmpty() || "unknown".equalsIgnoreCase(address)) {
                address = request.getRemoteAddr();
            }
        }
        return address;
    }
}

package icu.pymiliblog.pymillsblog.utils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtils {
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

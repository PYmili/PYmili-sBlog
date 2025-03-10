package icu.pymiliblog.pymillsblog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 用户密码的工具类
 * @author PYmili
 */
public class PasswordUtils {

    /**
     * 将密码hash处理
     * @param password {@link String} 需要处理的密码
     * @param salt {@link String} hash 盐
     * @return {@link String}
     * @throws NoSuchAlgorithmException
     */
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] saltBytes = JwtUtils.hexToBytes(salt);
        byte[] passwordBytes = password.getBytes();
        byte[] combined = new byte[saltBytes.length + passwordBytes.length];
        System.arraycopy(saltBytes, 0, combined, 0, saltBytes.length);
        System.arraycopy(passwordBytes, 0, combined, saltBytes.length, passwordBytes.length);
        byte[] hashBytes = md.digest(combined);
        return JwtUtils.bytesToHex(hashBytes);
    }
}

package icu.pymiliblog.pymillsblog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

@Slf4j
public class JwtUtils {

    private static final String SECRET_KEY = "J+FD2Ebm7e81fVPlrr8/kr1PdnR2KjLB4IFu6wtwOVg=";
    private static final int SALT_LENGTH = 16;  // 盐的长度
    private static final int KEY_LENGTH = 256; // 密钥长度

    /**
     * 生成一个256位的随机密钥，适合用于HMAC-SHA算法。
     * @return 生成的密钥的Base64编码字符串。
     * @throws NoSuchAlgorithmException 如果指定的算法不存在。
     */
    public static String generateSecretKey() throws NoSuchAlgorithmException {
        // 创建一个KeyGenerator实例，用于生成密钥
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        // 使用SecureRandom作为随机源
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        // 初始化KeyGenerator，设置密钥大小为256位
        keyGen.init(KEY_LENGTH, secureRandom);
        // 生成密钥
        SecretKey secretKey = keyGen.generateKey();
        // 将密钥转换为Base64编码的字符串
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] hexToBytes(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int index = i * 2;
            int intValue = Integer.parseInt(hexString.substring(index, index + 2), 16);
            bytes[i] = (byte) (intValue & 0xff);
        }
        return bytes;
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    public static String createJwt(long days, Map<String, Object> claims, String subject) {
        // 生成JWT的时间
        long expMillis = System.currentTimeMillis() + days * (1000 * 60 * 60 * 24);
        Date exp = new Date(expMillis);

        // 生成 HMAC 密钥
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        // 设置 JWT 的 body
        JwtBuilder builder = Jwts.builder()
                // signature 算法
                .signWith(key)
                // payload
                .claims(claims)
                // 主题
                .subject(subject)
                // TTL
                .expiration(exp)
                // uuid
                .id(UUID.randomUUID().toString());
        return builder.compact();
    }

    public static Claims parseJWT(String token) {
        //生成 HMAC 密钥，根据提供的字节数组长度选择适当的 HMAC 算法，并返回相应的 SecretKey 对象。
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        // 得到DefaultJwtParser
        JwtParser jwtParser = Jwts.parser()
                // 设置签名的秘钥
                .verifyWith(key)
                .build();
        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        return jws.getPayload();
    }

    public static boolean verify(String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            return false;
        }
        if (!jwt.startsWith("Bearer ")) return false;
        log.info("verify jwt: {}", jwt);
        Claims claims = parseJWT(jwt.split(" ")[1]);
        return !claims.isEmpty();
    }
}

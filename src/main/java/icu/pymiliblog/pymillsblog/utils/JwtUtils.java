package icu.pymiliblog.pymillsblog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.security.Keys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class JwtUtils {

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
        keyGen.init(256, secureRandom);
        // 生成密钥
        SecretKey secretKey = keyGen.generateKey();
        // 将密钥转换为Base64编码的字符串
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static String createJwt(String secretKey, long days, Map<String, Object> claims, String subject) {
        // 生成JWT的时间
        long expMillis = System.currentTimeMillis() + days * (1000 * 60 * 60 * 24);
        Date exp = new Date(expMillis);

        // 生成 HMAC 密钥
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

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

    public static Claims parseJWT(String secretKey, String token) {
        //生成 HMAC 密钥，根据提供的字节数组长度选择适当的 HMAC 算法，并返回相应的 SecretKey 对象。
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 得到DefaultJwtParser
        JwtParser jwtParser = Jwts.parser()
                // 设置签名的秘钥
                .verifyWith(key)
                .build();
        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        return jws.getPayload();
    }
}

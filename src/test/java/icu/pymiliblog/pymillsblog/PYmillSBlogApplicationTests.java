package icu.pymiliblog.pymillsblog;

import icu.pymiliblog.pymillsblog.mapper.UserMapper;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import icu.pymiliblog.pymillsblog.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class PYmillSBlogApplicationTests {

    @Autowired
    private UserMapper mapper;

    @Test
    void testFindUser() {
        UserPojo user = new UserPojo();
        user.setName("PYmili");
        // user.setPassword("1234");
        // user.setEmail("mc2005wj@163.com");
        UserPojo result = mapper.findByPojo(user);
        System.out.println("test select user result: " + result);
    }

    @Test
    void testAddUser() {
        UserPojo user = new UserPojo();
        user.setName("PYmili");
        user.setPasswordHash("1234");
        user.setEmail("mc2005wj@163.com");
        int result = mapper.addUser(user);
        System.out.println("test add user result: " + result);
    }

    @Test
    void testJWTUtils() throws NoSuchAlgorithmException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "PYmili");
        String jwtToken = JwtUtils.createJwt(1, map, "test");
        Claims parse = JwtUtils.parseJWT(jwtToken);
        System.out.println(parse.toString());
    }

    @Test
    void testGenToken() throws NoSuchAlgorithmException {
        // byte[] decode = Base64.getDecoder().decode(JwtUtils.generateSecretKey());
        // String s = new String(decode, StandardCharsets.UTF_8);
        System.out.println(JwtUtils.generateSecretKey());
    }

    @Test
    void hashTest() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] pwdByte = "1234".getBytes();
        byte[] hashBytes = md.digest(pwdByte);
        System.out.println(JwtUtils.bytesToHex(hashBytes));
    }

}

package icu.pymiliblog.pymillsblog;

import icu.pymiliblog.pymillsblog.mapper.UserMapper;
import icu.pymiliblog.pymillsblog.pojo.UserPojo;
import icu.pymiliblog.pymillsblog.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        UserPojo result = mapper.findUser(user);
        System.out.println("test select user result: " + result);
    }

    @Test
    void testAddUser() {
        UserPojo user = new UserPojo();
        user.setName("PYmili");
        user.setPassword("1234");
        user.setEmail("mc2005wj@163.com");
        user.setToken("token");
        int result = mapper.addUser(user);
        System.out.println("test add user result: " + result);
    }

    @Test
    void testJWTUtils() throws NoSuchAlgorithmException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "PYmili");
        String secretKey =  JwtUtils.generateSecretKey();
        String jwtToken = JwtUtils.createJwt(secretKey, 1, map, "test");
        Claims parse = JwtUtils.parseJWT(secretKey, jwtToken);
        System.out.println(parse.toString());
    }

}

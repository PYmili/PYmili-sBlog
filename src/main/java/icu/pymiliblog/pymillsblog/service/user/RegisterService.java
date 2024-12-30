package icu.pymiliblog.pymillsblog.service.user;

import icu.pymiliblog.pymillsblog.mapper.UserMapper;
import icu.pymiliblog.pymillsblog.pojo.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.UserPojo;
import icu.pymiliblog.pymillsblog.utils.Base64Utils;
import icu.pymiliblog.pymillsblog.utils.JwtUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterService {

    @Autowired
    private UserMapper mapper;

    public ResponseEntity<ResultPojo> register(UserPojo userData, Logger logger) throws NoSuchAlgorithmException {
        // 用户的json数据解码
        userData.setPassword(Base64Utils.decode(userData.getPassword()));
        userData.setName(Base64Utils.decode(userData.getName()));
        logger.info("register user name: {}", userData.getName());

        // 查询用户是否重复
        UserPojo findUser = new UserPojo();
        findUser.setName(userData.getName());
        UserPojo findResult = mapper.findUser(findUser);
        if (findResult != null) {
            return new ResponseEntity<>(
                    new ResultPojo(HttpStatus.NOT_FOUND.value(), "用户已存在"),
                    HttpStatus.NOT_FOUND
            );
        }

        // 生成 token 和 secretKey
        String secretKey = JwtUtils.generateSecretKey();
        Map<String, Object> map = new HashMap<>();
        map.put("name", userData.getName());
        String token = JwtUtils.createJwt(secretKey, 1, map, "register");

        // 写入token和secretKey
        userData.setToken(token);
        userData.setSecretKey(secretKey);
        userData.setEmail("null");

        logger.info("register data: {}", userData);

        // 添加用户
        int result = mapper.addUser(userData);
        if (result != 1) return new ResponseEntity<>(
                new ResultPojo(HttpStatus.INTERNAL_SERVER_ERROR.value(), "database error"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return ResponseEntity.ok(new ResultPojo(HttpStatus.OK.value(), "success"));
    }

}

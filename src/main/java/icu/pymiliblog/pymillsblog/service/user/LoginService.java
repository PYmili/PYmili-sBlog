package icu.pymiliblog.pymillsblog.service.user;

import icu.pymiliblog.pymillsblog.mapper.UserMapper;
import icu.pymiliblog.pymillsblog.pojo.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.UserPojo;
import icu.pymiliblog.pymillsblog.pojo.UserRequestPojo;
import icu.pymiliblog.pymillsblog.utils.Base64Utils;
import icu.pymiliblog.pymillsblog.utils.JwtUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private UserMapper mapper;

    public ResponseEntity<ResultPojo> login(UserPojo userData, Logger logger) throws NoSuchAlgorithmException {
        // 用户的json数据解码
        userData.setPassword(Base64Utils.decode(userData.getPassword()));
        userData.setName(Base64Utils.decode(userData.getName()));
        logger.info("login user name: {}", userData.getName());
        logger.info("login user password is empty: {}", userData.getPassword().isEmpty());

        // 查询用户
        UserPojo findResult = mapper.findUser(userData);
        // 未查询到则返回错误
        if (findResult == null) {
            logger.warn("not user: {}", userData.getName());
            return ResultPojo.not_found("用户或密码错误！");
        }

        // 生成 jwt token
        Map<String, Object> map = new HashMap<>();
        map.put("name", userData.getName());

        // 生成新的secretKey
        String secretKey = JwtUtils.generateSecretKey();
        if (secretKey == null) {
            secretKey = findResult.getSecretKey();
        }
        String JwtToken = JwtUtils.createJwt(secretKey, 1, map, "login");

        // 写入新的jwt至数据库
        int updateResult = mapper.updateUser(
                new UserRequestPojo(userData.getName(), JwtToken),
                userData
        );
        if (updateResult != 1) {
            logger.warn("update user failed");
            return ResultPojo.not_found("更新数据失败，请联系管理员！");
        }

        return ResponseEntity.ok().body(new ResultPojo(200,  JwtToken));
    }

}

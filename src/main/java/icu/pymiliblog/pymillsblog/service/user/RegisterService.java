package icu.pymiliblog.pymillsblog.service.user;

import icu.pymiliblog.pymillsblog.mapper.UserMapper;
import icu.pymiliblog.pymillsblog.pojo.ResultPojo;
import icu.pymiliblog.pymillsblog.pojo.user.UserPojo;
import icu.pymiliblog.pymillsblog.utils.JwtUtils;
import icu.pymiliblog.pymillsblog.utils.PasswordUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RegisterService {

    private final UserMapper userMapper;

    public RegisterService(UserMapper mapper) {
        this.userMapper = mapper;
    }

    private boolean existUser(String username) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(username);
        UserPojo findResult = userMapper.findUser(userPojo);
        return findResult != null;
    }

    private Integer addUserToDataBase(UserPojo userPojo) {
        return userMapper.addUser(userPojo);
    }

    @SneakyThrows
    public ResponseEntity<ResultPojo> register(UserPojo requestBody) {
        // 查看用户是否重复
        if (existUser(requestBody.getName())) {
            log.warn("user register service: 用户 {} 已经存在！", requestBody.getName());
            return ResultPojo.not_found("用户存在！");
        }

        // 注册用户
        String salt = JwtUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(requestBody.getPasswordHash(), salt);
        requestBody.setSalt(salt);
        requestBody.setPasswordHash(hashedPassword);
        Integer id = addUserToDataBase(requestBody);
        if (id == null || id == 0) {
            return new ResponseEntity<>(
                    new ResultPojo(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "注册失败！"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // 生成jwt
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("user", requestBody.getName());
        String jwt = JwtUtils.createJwt(1, map, "register");

        return ResultPojo.ok(jwt);
    }

}
